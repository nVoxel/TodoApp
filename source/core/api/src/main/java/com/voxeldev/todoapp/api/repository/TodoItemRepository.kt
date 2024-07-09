package com.voxeldev.todoapp.api.repository

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.api.model.TodoItemList
import com.voxeldev.todoapp.api.request.TodoItemModifyRequest
import kotlinx.coroutines.flow.StateFlow

/**
 * @author nvoxel
 */
interface TodoItemRepository {

    suspend fun getAllFlow(): Result<StateFlow<TodoItemList>>

    suspend fun createItem(request: TodoItemModifyRequest): Result<Unit>

    suspend fun getSingle(id: String): Result<TodoItem>

    suspend fun updateItem(request: TodoItemModifyRequest): Result<Unit>

    suspend fun deleteItem(id: String): Result<Unit>

    suspend fun refreshData(): Result<Unit>

    suspend fun clearLocalData(): Result<Unit>
}
