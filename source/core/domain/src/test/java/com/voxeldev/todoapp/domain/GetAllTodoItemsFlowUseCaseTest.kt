package com.voxeldev.todoapp.domain

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.api.model.TodoItemImportance
import com.voxeldev.todoapp.api.model.TodoItemList
import com.voxeldev.todoapp.api.repository.TodoItemRepository
import com.voxeldev.todoapp.domain.base.UseCaseTest
import com.voxeldev.todoapp.domain.usecase.base.BaseUseCase
import com.voxeldev.todoapp.domain.usecase.todoitem.GetAllTodoItemsFlowUseCase
import com.voxeldev.todoapp.utils.exceptions.NetworkNotAvailableException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import kotlin.test.assertEquals

/**
 * @author nvoxel
 */
class GetAllTodoItemsFlowUseCaseTest : UseCaseTest() {

    @Test
    fun `GetAllTodoItemsFlowUseCase gets data from the repository`() = runTest {
        val todoItemRepository = mock<TodoItemRepository>()

        val useCase = GetAllTodoItemsFlowUseCase(
            todoItemRepository = todoItemRepository,
            coroutineDispatcherProvider = coroutineDispatcherProvider,
        )

        useCase.run(params = BaseUseCase.NoParams)

        verify(todoItemRepository, times(1)).getAllFlow()
    }

    @Test
    fun `GetAllTodoItemsFlowUseCase returns same value`() = runTest {
        val resultList = listOf(
            TodoItem(
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

        val repositoryResult = Result.success(
            value = MutableStateFlow(value = TodoItemList(list = resultList, isOffline = true)),
        )

        checkGetAllTodoItemsFlowUseCaseReturn(repositoryResult = repositoryResult)
    }

    @Test
    fun `GetAllTodoItemsFlowUseCase returns exception`() {
        val repositoryResult = Result.failure<StateFlow<TodoItemList>>(
            exception = NetworkNotAvailableException(),
        )

        checkGetAllTodoItemsFlowUseCaseReturn(repositoryResult = repositoryResult)
    }

    private fun checkGetAllTodoItemsFlowUseCaseReturn(repositoryResult: Result<StateFlow<TodoItemList>>) = runTest {
        val todoItemRepository = mock<TodoItemRepository> {
            onBlocking { getAllFlow() } doReturn repositoryResult
        }

        val useCase = GetAllTodoItemsFlowUseCase(
            todoItemRepository = todoItemRepository,
            coroutineDispatcherProvider = coroutineDispatcherProvider,
        )

        val useCaseResult = useCase.run(params = BaseUseCase.NoParams)

        assertEquals(
            expected = repositoryResult,
            actual = useCaseResult,
            message = "UseCase should return the same value Repository returns",
        )
    }
}
