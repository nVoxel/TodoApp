package com.voxeldev.todoapp.di.stubs.repository

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.api.model.TodoItemImportance
import com.voxeldev.todoapp.api.model.TodoItemList
import com.voxeldev.todoapp.api.repository.TodoItemRepository
import com.voxeldev.todoapp.api.request.TodoItemModifyRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * @author nvoxel
 */
class StubTodoItemRepository @Inject constructor() : TodoItemRepository {

    private val todoItemsFlow = MutableStateFlow(
        value = TodoItemList(
            list = emptyList(),
            isOffline = false,
        ),
    )

    init {
        resetData()
    }

    override suspend fun getAllFlow(): Result<StateFlow<TodoItemList>> = Result.success(value = todoItemsFlow)

    override suspend fun createItem(request: TodoItemModifyRequest): Result<Unit> {
        val item = requestToItem(request = request)

        return runCatching {
            todoItemsFlow.update {
                it.copy(list = it.list + item)
            }
        }
    }

    override suspend fun getSingle(id: String): Result<TodoItem> = Result.success(
        value = TodoItem(
            id = "1",
            text = "First task",
            importance = TodoItemImportance.Normal,
            deadlineTimestamp = null,
            isComplete = false,
            creationTimestamp = 1_000_000L,
            modifiedTimestamp = 2_000_000L,
            lastUpdatedBy = "device-id",
        ),
    )

    override suspend fun updateItem(request: TodoItemModifyRequest): Result<Unit> {
        val item = requestToItem(request = request)

        return runCatching {
            todoItemsFlow.update {
                val list = it.list.map { listItem -> if (listItem.id == request.id) item else listItem }
                it.copy(list = list)
            }
        }
    }

    override suspend fun deleteItem(id: String): Result<Unit> = runCatching {
        todoItemsFlow.update {
            val list = it.list.filterNot { listItem -> listItem.id == id }
            it.copy(list = list)
        }
    }

    override suspend fun refreshData(): Result<Unit> = Result.success(Unit)

    override suspend fun clearLocalData(): Result<Unit> = runCatching { resetData() }

    private fun resetData() {
        todoItemsFlow.update {
            TodoItemList(
                list = listOf(
                    TodoItem(
                        id = "1",
                        text = "First task",
                        importance = TodoItemImportance.Normal,
                        deadlineTimestamp = null,
                        isComplete = false,
                        creationTimestamp = 1_000_000L,
                        modifiedTimestamp = 2_000_000L,
                        lastUpdatedBy = DEVICE_ID,
                    ),
                ),
                isOffline = false,
            )
        }
    }

    private fun requestToItem(request: TodoItemModifyRequest): TodoItem =
        TodoItem(
            id = request.id,
            text = request.text,
            importance = request.importance,
            deadlineTimestamp = request.deadlineTimestamp,
            isComplete = request.isComplete,
            creationTimestamp = request.creationTimestamp,
            modifiedTimestamp = request.modifiedTimestamp,
            lastUpdatedBy = DEVICE_ID,
        )

    private companion object {
        const val DEVICE_ID = "device-id"
    }
}
