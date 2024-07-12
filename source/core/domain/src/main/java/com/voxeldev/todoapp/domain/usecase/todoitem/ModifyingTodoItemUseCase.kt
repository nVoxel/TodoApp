package com.voxeldev.todoapp.domain.usecase.todoitem

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.api.repository.PreferencesRepository
import com.voxeldev.todoapp.api.repository.TodoItemListRepository
import com.voxeldev.todoapp.api.request.TodoItemModifyRequest
import com.voxeldev.todoapp.domain.usecase.base.BaseUseCase
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider

/**
 * Base use case for all modifying (but not deleting) requests.
 * Provides current revision and device ID.
 * @author nvoxel
 */
abstract class ModifyingTodoItemUseCase(
    private val todoItemListRepository: TodoItemListRepository,
    private val preferencesRepository: PreferencesRepository,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : BaseUseCase<TodoItem, Unit>(coroutineDispatcherProvider = coroutineDispatcherProvider) {

    protected suspend fun getRevision(todoItem: TodoItem): Result<Unit> = todoItemListRepository.getRevision().fold(
        onSuccess = { revision -> getDeviceId(todoItem = todoItem, revision = revision) },
        onFailure = { exception -> Result.failure(exception = exception) },
    )

    private suspend fun getDeviceId(todoItem: TodoItem, revision: Int): Result<Unit> = preferencesRepository.getDeviceID().fold(
        onSuccess = { deviceId ->
            return modifyTodoItem(
                todoItemModifyRequest = TodoItemModifyRequest(
                    todoItem = todoItem,
                    revision = revision,
                    deviceId = deviceId,
                ),
                onSuccessCallback = { todoItemListRepository.refreshData() },
            )
        },
        onFailure = { exception -> Result.failure(exception = exception) },
    )

    abstract suspend fun modifyTodoItem(
        todoItemModifyRequest: TodoItemModifyRequest,
        onSuccessCallback: suspend () -> Unit,
    ): Result<Unit>
}
