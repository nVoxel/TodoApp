package com.voxeldev.todoapp.repository

import com.voxeldev.todoapp.api.extensions.toModifyRequest
import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.api.model.TodoItemList
import com.voxeldev.todoapp.api.repository.PreferencesRepository
import com.voxeldev.todoapp.api.repository.TodoItemRepository
import com.voxeldev.todoapp.api.request.TodoItemModifyRequest
import com.voxeldev.todoapp.database.todo.datasource.TodoItemLocalDataSource
import com.voxeldev.todoapp.network.todoapi.TodoItemListRemoteDataSource
import com.voxeldev.todoapp.network.todoapi.TodoItemRemoteDataSource
import com.voxeldev.todoapp.network.todoapi.datasource.response.TodoListResponse
import com.voxeldev.todoapp.repository.mapper.TodoItemLocalMapper
import com.voxeldev.todoapp.repository.mapper.TodoItemModifyRequestMapper
import com.voxeldev.todoapp.repository.mapper.TodoItemRemoteMapper
import com.voxeldev.todoapp.repository.utils.DataMerger
import com.voxeldev.todoapp.utils.exceptions.ItemNotFoundException
import com.voxeldev.todoapp.utils.exceptions.NetworkNotAvailableException
import com.voxeldev.todoapp.utils.exceptions.OtherNetworkException
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * @author nvoxel
 */
internal class DefaultTodoItemRepository @Inject constructor(
    private val todoItemListRemoteDataSource: TodoItemListRemoteDataSource,
    private val todoItemRemoteDataSource: TodoItemRemoteDataSource,
    private val todoItemLocalDataSource: TodoItemLocalDataSource,
    private val todoItemRemoteMapper: TodoItemRemoteMapper,
    private val todoItemLocalMapper: TodoItemLocalMapper,
    private val todoItemModifyRequestMapper: TodoItemModifyRequestMapper,
    private val preferencesRepository: PreferencesRepository,
    private val dataMerger: DataMerger,
) : TodoItemRepository {

    private val _todoItemsFlow: MutableStateFlow<TodoItemList> = MutableStateFlow(
        TodoItemList(
            list = emptyList(),
            isOffline = false,
        ),
    )
    private val todoItemsFlow: StateFlow<TodoItemList> = _todoItemsFlow.asStateFlow()

    private val deviceIdFlow: MutableStateFlow<String?> = MutableStateFlow(value = null)

    private val lastRevisionFlow: MutableStateFlow<Int?> = MutableStateFlow(value = null)

    override suspend fun getAllFlow(): Result<StateFlow<TodoItemList>> {
        return refreshData().fold(
            onSuccess = { Result.success(todoItemsFlow) },
            onFailure = { exception -> Result.failure(exception = exception) },
        )
    }

    override suspend fun createItem(request: TodoItemModifyRequest): Result<Unit> {
        todoItemRemoteDataSource.createItem(
            request = request,
            deviceId = deviceIdFlow.value ?: loadDeviceId(),
            revision = getRevision(),
        ).fold(
            onSuccess = { return refreshData() },
            onFailure = { exception ->
                return handleRemoteException(exception = exception) {
                    createItemLocally(request = request)
                }
            },
        )
    }

    private suspend fun createItemLocally(request: TodoItemModifyRequest): Result<Unit> = runCatching {
        todoItemLocalDataSource.insert(
            todoItem = todoItemModifyRequestMapper.toModel(
                request = request,
                deviceId = deviceIdFlow.value ?: loadDeviceId(),
            ),
            revision = getRevision(),
        ).onSuccess {
            todoItemLocalDataSource.setShouldBeCreated(
                id = request.id,
                shouldBeCreated = true,
            )

            refreshData()
        }
    }

    override suspend fun getSingle(id: String): Result<TodoItem> {
        return todoItemRemoteDataSource.getSingle(id = id).fold(
            onSuccess = { response ->
                lastRevisionFlow.update { response.revision }
                Result.success(todoItemRemoteMapper.toModel(todoItemData = response.element))
            },
            onFailure = { exception ->
                return handleRemoteException(exception = exception) {
                    todoItemsFlow.value.list.find { it.id == id }?.let { localItem ->
                        Result.success(localItem)
                    } ?: Result.failure(exception = ItemNotFoundException(id = id))
                }
            },
        )
    }

    override suspend fun updateItem(request: TodoItemModifyRequest): Result<Unit> {
        todoItemRemoteDataSource.updateItem(
            request = request,
            deviceId = deviceIdFlow.value ?: loadDeviceId(),
            revision = getRevision(),
        ).fold(
            onSuccess = { return refreshData() },
            onFailure = { exception ->
                return handleRemoteException(exception = exception) {
                    updateItemLocally(request = request)
                }
            },
        )
    }

    private suspend fun updateItemLocally(request: TodoItemModifyRequest): Result<Unit> = runCatching {
        todoItemLocalDataSource.update(
            todoItem = todoItemModifyRequestMapper.toModel(
                request = request,
                deviceId = deviceIdFlow.value ?: loadDeviceId(),
            ),
            revision = getRevision(),
        ).onSuccess {
            todoItemLocalDataSource.setShouldBeUpdated(
                id = request.id,
                shouldBeUpdated = true,
            )

            refreshData()
        }
    }

    override suspend fun deleteItem(id: String): Result<Unit> {
        todoItemRemoteDataSource.deleteItem(
            id = id,
            revision = getRevision(),
        ).fold(
            onSuccess = { return refreshData() },
            onFailure = { exception ->
                return handleRemoteException(exception = exception) {
                    deleteItemLocally(id = id)
                }
            },
        )
    }

    private suspend fun deleteItemLocally(id: String): Result<Unit> = runCatching {
        todoItemLocalDataSource.setShouldBeDeleted(id = id, shouldBeDeleted = true).onSuccess {
            refreshData()
        }
    }

    override suspend fun refreshData(): Result<Unit> = runCatching {
        var isOffline = false

        todoItemListRemoteDataSource.getAll().fold(
            onSuccess = { remoteList ->
                lastRevisionFlow.update { remoteList.revision }
                synchronizeLists(remoteList = remoteList)
            },
            onFailure = { exception ->
                if (exception !is NetworkNotAvailableException) return Result.failure(exception = exception)
                isOffline = true
            },
        )

        todoItemLocalDataSource.getAllAsList().onSuccess { localList ->
            _todoItemsFlow.update {
                TodoItemList(
                    list = localList
                        .filterNot { it.shouldBeDeleted }
                        .map { todoItemLocalMapper.toModel(todoEntity = it) },
                    isOffline = isOffline,
                )
            }
        }.onFailure { exception ->
            return Result.failure(exception = exception)
        }
    }

    override suspend fun clearLocalData(): Result<Unit> = runCatching {
        todoItemLocalDataSource.clearTable()
    }

    private suspend fun synchronizeLists(remoteList: TodoListResponse) {
        val localList = todoItemLocalDataSource.getAllAsList().getOrThrow()
        val deviceId = preferencesRepository.getDeviceID().getOrThrow()

        dataMerger.mergeLists(
            localList = localList,
            remoteList = remoteList,
            deviceId = deviceId,
            onRequestCreateLocalItem = { todoItem ->
                todoItemLocalDataSource.insert(
                    todoItem = todoItem,
                    revision = getRevision(),
                )
            },
            onRequestUpdateLocalItem = { todoItem ->
                todoItemLocalDataSource.update(
                    todoItem = todoItem,
                    revision = getRevision(),
                )
            },
            onRequestDeleteLocalItem = { id ->
                todoItemLocalDataSource.deleteById(id = id)
            },
            onRequestCreateRemoteItem = { todoItem ->
                todoItemRemoteDataSource.createItem(
                    request = todoItem.toModifyRequest(),
                    deviceId = deviceId,
                    revision = getRevision(),
                ).onSuccess {
                    lastRevisionFlow.update { getRevision() + 1 }

                    todoItemLocalDataSource.setShouldBeCreated(
                        id = todoItem.id,
                        shouldBeCreated = false,
                    )

                    todoItemLocalDataSource.setShouldBeUpdated(
                        id = todoItem.id,
                        shouldBeUpdated = false,
                    )
                }
            },
            onRequestUpdateRemoteItem = { todoItem ->
                todoItemRemoteDataSource.updateItem(
                    request = todoItem.toModifyRequest(),
                    deviceId = deviceId,
                    revision = getRevision(),
                ).onSuccess {
                    lastRevisionFlow.update { getRevision() + 1 }

                    todoItemLocalDataSource.setShouldBeUpdated(
                        id = todoItem.id,
                        shouldBeUpdated = false,
                    )
                }.onFailure { exception ->
                    if (exception.causedByUnsynchronizedData()) {
                        todoItemLocalDataSource.deleteById(id = todoItem.id)
                    }
                }
            },
            onRequestDeleteRemoteItem = { id ->
                todoItemRemoteDataSource.deleteItem(
                    id = id,
                    revision = getRevision(),
                ).onSuccess {
                    lastRevisionFlow.update { getRevision() + 1 }

                    todoItemLocalDataSource.deleteById(id = id)
                }.onFailure { exception ->
                    if (exception.causedByUnsynchronizedData()) {
                        todoItemLocalDataSource.deleteById(id = id)
                    }
                }
            },
        )
    }

    private fun loadDeviceId(): String =
        preferencesRepository.getDeviceID().getOrThrow()
            .also { deviceId -> deviceIdFlow.update { deviceId } }

    private suspend fun getRevision(): Int {
        val lastRevision = lastRevisionFlow.value
        if (lastRevision != null) return lastRevision

        todoItemLocalDataSource.getAllAsList().fold(
            onSuccess = { localList ->
                if (localList.isEmpty()) return FALLBACK_REVISION
                return localList.maxOf { localItem -> localItem.revision }
            },
            onFailure = { return FALLBACK_REVISION },
        )
    }

    private suspend fun <T> handleRemoteException(exception: Throwable, doLocalOperation: suspend () -> Result<T>): Result<T> {
        if (exception !is NetworkNotAvailableException) {
            refreshData()
            return Result.failure(exception = exception)
        }

        return doLocalOperation()
    }

    private fun Throwable.causedByUnsynchronizedData() = this is OtherNetworkException && responseCode == HttpStatusCode.NotFound.value

    private companion object {
        const val FALLBACK_REVISION = 0
    }
}
