package com.voxeldev.todoapp.api.repository

import com.voxeldev.todoapp.api.model.TodoItem

/**
 * @author nvoxel
 */
interface TodoItemRepository {

    suspend fun createItem(item: TodoItem, revision: Int): Result<Unit>

    suspend fun getSingle(id: String): Result<TodoItem>

    suspend fun updateItem(item: TodoItem, revision: Int): Result<Unit>

    suspend fun deleteItem(id: String, revision: Int): Result<Unit>
}
