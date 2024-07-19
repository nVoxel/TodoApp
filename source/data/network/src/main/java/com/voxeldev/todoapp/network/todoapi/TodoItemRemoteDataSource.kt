package com.voxeldev.todoapp.network.todoapi

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.api.request.TodoItemModifyRequest
import com.voxeldev.todoapp.network.todoapi.datasource.response.TodoSingleResponse

/**
 * Provides CRUD interface of [TodoItem].
 * @author nvoxel
 */
interface TodoItemRemoteDataSource {

    suspend fun createItem(
        request: TodoItemModifyRequest,
        deviceId: String,
        revision: Int,
    ): Result<Unit>

    suspend fun getSingle(id: String): Result<TodoSingleResponse>

    suspend fun updateItem(
        request: TodoItemModifyRequest,
        deviceId: String,
        revision: Int,
    ): Result<Unit>

    suspend fun deleteItem(id: String, revision: Int): Result<Unit>
}
