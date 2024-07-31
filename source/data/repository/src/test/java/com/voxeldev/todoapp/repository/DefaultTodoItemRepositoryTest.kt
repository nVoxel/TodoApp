package com.voxeldev.todoapp.repository

import com.voxeldev.todoapp.api.model.TodoItemImportance
import com.voxeldev.todoapp.api.model.TodoItemList
import com.voxeldev.todoapp.api.repository.PreferencesRepository
import com.voxeldev.todoapp.api.request.TodoItemModifyRequest
import com.voxeldev.todoapp.database.todo.TodoEntity
import com.voxeldev.todoapp.database.todo.TodoEntityList
import com.voxeldev.todoapp.database.todo.datasource.TodoItemLocalDataSource
import com.voxeldev.todoapp.network.todoapi.TodoItemListRemoteDataSource
import com.voxeldev.todoapp.network.todoapi.TodoItemRemoteDataSource
import com.voxeldev.todoapp.network.todoapi.datasource.TodoItemData
import com.voxeldev.todoapp.network.todoapi.datasource.response.TodoListResponse
import com.voxeldev.todoapp.repository.mapper.TodoItemLocalMapper
import com.voxeldev.todoapp.repository.mapper.TodoItemModifyRequestMapper
import com.voxeldev.todoapp.repository.mapper.TodoItemRemoteMapper
import com.voxeldev.todoapp.repository.utils.DefaultDataMerger
import com.voxeldev.todoapp.utils.exceptions.NetworkNotAvailableException
import com.voxeldev.todoapp.utils.exceptions.OtherNetworkException
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import kotlin.test.assertTrue

/**
 * @author nvoxel
 */
class DefaultTodoItemRepositoryTest {

    private val deviceId = "device-id"
    private val revision = 100

    private val expectedRemoteResult = Result.success(
        value = TodoListResponse(
            status = "ok",
            list = listOf(
                TodoItemData(
                    id = "1",
                    text = "First task",
                    importance = "basic",
                    deadlineTimestamp = 2_000_000L,
                    isComplete = true,
                    color = null,
                    creationTimestamp = 1_000_000L,
                    modifiedTimestamp = 1_500_000L,
                    lastUpdatedBy = deviceId,
                ),
            ),
            revision = revision,
        ),
    )

    private val expectedLocalResult = Result.success(
        value = listOf(
            TodoEntity(
                id = "1",
                text = "First task",
                importance = TodoItemImportance.Normal,
                deadlineTimestamp = 2_000_000L,
                isComplete = true,
                creationTimestamp = 1_000_000L,
                modifiedTimestamp = 1_500_000L,
                lastUpdatedBy = deviceId,
                revision = revision,
            ),
        ),
    )

    @Test
    fun `DefaultTodoItemRepository returns items from remote`() = runTest {
        val remoteDataSource = mock<TodoItemListRemoteDataSource> {
            onBlocking { getAll() } doReturn expectedRemoteResult
        }

        val localDataSource = mock<TodoItemLocalDataSource> {
            onBlocking { getAllAsList() } doReturn expectedLocalResult
        }

        val repository = DefaultTodoItemRepository(
            todoItemListRemoteDataSource = remoteDataSource,
            todoItemRemoteDataSource = mock(),
            todoItemLocalDataSource = localDataSource,
            todoItemRemoteMapper = TodoItemRemoteMapper(),
            todoItemLocalMapper = TodoItemLocalMapper(),
            todoItemModifyRequestMapper = TodoItemModifyRequestMapper(),
            preferencesRepository = mock(),
            dataMerger = DefaultDataMerger(
                todoItemLocalMapper = TodoItemLocalMapper(),
                todoItemRemoteMapper = TodoItemRemoteMapper(),
            ),
        )

        val repositoryResult = repository.getAllFlow()

        assertTrue(
            message = "Repository should return success. Instead got: ${repositoryResult.exceptionOrNull()?.message}",
        ) {
            repositoryResult.isSuccess
        }

        assertTrue(message = "Repository should return all items") {
            compareRemoteLists(
                remoteResult = expectedRemoteResult.getOrThrow(),
                repositoryResult = repositoryResult.getOrThrow().value,
            )
        }
    }

    @Test
    fun `DefaultTodoItemRepository returns items from local when network unavailable`() = runTest {
        val expectedRemoteResult = Result.failure<TodoListResponse>(
            exception = NetworkNotAvailableException(),
        )

        val remoteDataSource = mock<TodoItemListRemoteDataSource> {
            onBlocking { getAll() } doReturn expectedRemoteResult
        }

        val localDataSource = mock<TodoItemLocalDataSource> {
            onBlocking { getAllAsList() } doReturn expectedLocalResult
        }

        val repository = DefaultTodoItemRepository(
            todoItemListRemoteDataSource = remoteDataSource,
            todoItemRemoteDataSource = mock(),
            todoItemLocalDataSource = localDataSource,
            todoItemRemoteMapper = TodoItemRemoteMapper(),
            todoItemLocalMapper = TodoItemLocalMapper(),
            todoItemModifyRequestMapper = TodoItemModifyRequestMapper(),
            preferencesRepository = mock(),
            dataMerger = DefaultDataMerger(
                todoItemLocalMapper = TodoItemLocalMapper(),
                todoItemRemoteMapper = TodoItemRemoteMapper(),
            ),
        )

        val repositoryResult = repository.getAllFlow()

        assertTrue(
            message = "Repository should return success. Instead got: ${repositoryResult.exceptionOrNull()?.message}",
        ) {
            repositoryResult.isSuccess
        }

        assertTrue("Repository should return offline flag") {
            repositoryResult.getOrThrow().value.isOffline
        }

        assertTrue(message = "Repository should return all items") {
            compareLocalLists(
                localResult = expectedLocalResult.getOrThrow(),
                repositoryResult = repositoryResult.getOrThrow().value,
            )
        }
    }

    @Test
    fun `DefaultTodoItemRepository calls refreshData when got unsynchronized data`() = runTest {
        val request = TodoItemModifyRequest(
            id = "1",
            text = "First task",
            importance = TodoItemImportance.Normal,
            deadlineTimestamp = 2_000_000L,
            isComplete = true,
            creationTimestamp = 1_000_000L,
            modifiedTimestamp = 1_500_000L,
        )

        val expectedRemoteSingleResult = Result.failure<Unit>(
            exception = OtherNetworkException(responseCode = HttpStatusCode.NotFound.value),
        )

        val remoteListDataSource = mock<TodoItemListRemoteDataSource> {
            onBlocking { getAll() } doReturn expectedRemoteResult
        }

        val remoteSingleDataSource = mock<TodoItemRemoteDataSource> {
            onBlocking {
                createItem(
                    request = any(),
                    deviceId = any(),
                    revision = any(),
                )
            } doReturn expectedRemoteSingleResult
        }

        val localDataSource = mock<TodoItemLocalDataSource> {
            onBlocking { getAllAsList() } doReturn expectedLocalResult
        }

        val preferencesRepository = mock<PreferencesRepository> {
            onBlocking { getDeviceID() } doReturn Result.success(deviceId)
        }

        val repository = DefaultTodoItemRepository(
            todoItemListRemoteDataSource = remoteListDataSource,
            todoItemRemoteDataSource = remoteSingleDataSource,
            todoItemLocalDataSource = localDataSource,
            todoItemRemoteMapper = TodoItemRemoteMapper(),
            todoItemLocalMapper = TodoItemLocalMapper(),
            todoItemModifyRequestMapper = TodoItemModifyRequestMapper(),
            preferencesRepository = preferencesRepository,
            dataMerger = DefaultDataMerger(
                todoItemLocalMapper = TodoItemLocalMapper(),
                todoItemRemoteMapper = TodoItemRemoteMapper(),
            ),
        )

        val repositoryResult = repository.createItem(request = request)

        assertTrue(
            message = "Repository should return failure. Instead got: ${repositoryResult.exceptionOrNull()?.message}",
        ) {
            repositoryResult.isFailure
        }

        val exception = repositoryResult.exceptionOrNull()!!

        assertTrue(message = "Repository should return Result.failure with OtherNetworkException(404)") {
            exception is OtherNetworkException && exception.responseCode == HttpStatusCode.NotFound.value
        }

        verify(remoteSingleDataSource, times(1)).createItem(
            request = request,
            deviceId = deviceId,
            revision = revision,
        )

        verify(remoteListDataSource, times(1)).getAll()
    }

    private fun compareRemoteLists(
        remoteResult: TodoListResponse,
        repositoryResult: TodoItemList,
    ): Boolean {
        if (remoteResult.list.size != repositoryResult.list.size) return false

        val todoItemRemoteMapper = TodoItemRemoteMapper()
        val mappedRemoteResult = remoteResult.list.map { todoItemRemoteMapper.toModel(todoItemData = it) }

        return mappedRemoteResult == repositoryResult.list
    }

    private fun compareLocalLists(
        localResult: TodoEntityList,
        repositoryResult: TodoItemList,
    ): Boolean {
        if (localResult.size != repositoryResult.list.size) return false

        val todoItemLocalMapper = TodoItemLocalMapper()
        val mappedLocalResult = localResult.map { todoItemLocalMapper.toModel(todoEntity = it) }

        return mappedLocalResult == repositoryResult.list
    }
}
