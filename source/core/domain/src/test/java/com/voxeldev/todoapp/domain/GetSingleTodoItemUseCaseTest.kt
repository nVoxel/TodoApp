package com.voxeldev.todoapp.domain

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.api.model.TodoItemImportance
import com.voxeldev.todoapp.api.repository.TodoItemRepository
import com.voxeldev.todoapp.domain.base.UseCaseTest
import com.voxeldev.todoapp.domain.usecase.todoitem.GetSingleTodoItemUseCase
import com.voxeldev.todoapp.utils.exceptions.NetworkNotAvailableException
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import kotlin.test.assertEquals

/**
 * @author nvoxel
 */
class GetSingleTodoItemUseCaseTest : UseCaseTest() {

    @Test
    fun `GetSingleTodoItemUseCase gets data from the repository`() = runTest {
        val itemId = "test_item_id"

        val todoItemRepository = mock<TodoItemRepository>()

        val useCase = GetSingleTodoItemUseCase(
            todoItemRepository = todoItemRepository,
            coroutineDispatcherProvider = coroutineDispatcherProvider,
        )

        useCase.run(params = itemId)

        verify(todoItemRepository, times(1)).getSingle(id = itemId)
    }

    @Test
    fun `GetSingleTodoItemUseCase returns same value`() {
        val repositoryResult = Result.success(
            value = TodoItem(
                id = "1",
                text = "1234",
                importance = TodoItemImportance.Low,
                deadlineTimestamp = 2_000_000L,
                isComplete = false,
                creationTimestamp = 1_000_000L,
                modifiedTimestamp = 1_500_000L,
                lastUpdatedBy = "test",
            ),
        )

        checkGetSingleTodoItemUseCaseReturn(repositoryResult = repositoryResult)
    }

    @Test
    fun `GetSingleTodoItemUseCase returns exception`() {
        val repositoryResult = Result.failure<TodoItem>(
            exception = NetworkNotAvailableException(),
        )

        checkGetSingleTodoItemUseCaseReturn(repositoryResult = repositoryResult)
    }

    private fun checkGetSingleTodoItemUseCaseReturn(repositoryResult: Result<TodoItem>) = runTest {
        val todoItemRepository = mock<TodoItemRepository> {
            onBlocking { getSingle(any()) } doReturn repositoryResult
        }

        val useCase = GetSingleTodoItemUseCase(
            todoItemRepository = todoItemRepository,
            coroutineDispatcherProvider = coroutineDispatcherProvider,
        )

        val useCaseResult = useCase.run(params = "item")

        assertEquals(
            expected = repositoryResult,
            actual = useCaseResult,
            message = "UseCase should return the same value Repository returns",
        )
    }
}
