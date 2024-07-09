package com.voxeldev.todoapp.database.todo.datasource

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.database.todo.TodoEntityList

/**
 * @author nvoxel
 */
interface TodoItemLocalDataSource {

    suspend fun getAllAsList(): Result<TodoEntityList>

    suspend fun insert(todoItem: TodoItem, revision: Int): Result<Long>

    suspend fun update(todoItem: TodoItem, revision: Int): Result<Unit>

    suspend fun setShouldBeCreated(id: String, shouldBeCreated: Boolean): Result<Unit>

    suspend fun setShouldBeUpdated(id: String, shouldBeUpdated: Boolean): Result<Unit>

    suspend fun setShouldBeDeleted(id: String, shouldBeDeleted: Boolean): Result<Unit>

    suspend fun deleteById(id: String): Result<Unit>

    suspend fun clearTable(): Result<Unit>
}
