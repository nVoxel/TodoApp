package com.voxeldev.todoapp.api.repository

import com.voxeldev.todoapp.api.model.TodoItem
import kotlinx.coroutines.flow.Flow

/**
 * @author nvoxel
 */
interface TodoItemsRepository {

    fun createItem(item: TodoItem): Result<Unit>

    fun getAllFlow(): Result<Flow<List<TodoItem>>>

    fun getSingle(id: String): Result<TodoItem>

    fun updateItem(item: TodoItem): Result<Unit>

    fun deleteItem(id: String): Result<Unit>
}
