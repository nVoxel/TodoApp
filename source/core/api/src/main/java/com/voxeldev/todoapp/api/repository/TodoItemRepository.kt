package com.voxeldev.todoapp.api.repository

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.api.request.TodoItemModifyRequest

/**
 * Provides CRUD interface of [TodoItem].
 * @author nvoxel
 */
interface TodoItemRepository {

    suspend fun createItem(request: TodoItemModifyRequest): Result<Unit>

    suspend fun getSingle(id: String): Result<TodoItem>

    suspend fun updateItem(request: TodoItemModifyRequest): Result<Unit>

    suspend fun deleteItem(id: String, revision: Int): Result<Unit>
}
