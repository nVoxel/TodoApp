package com.voxeldev.todoapp.network.todoapi

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.network.todoapi.datasource.response.TodoListResponse

/**
 * Provides task list as well as the current data revision.
 * @author nvoxel
 */
interface TodoItemListRemoteDataSource {

    suspend fun getAll(): Result<TodoListResponse>

    suspend fun patchList(list: List<TodoItem>): Result<TodoListResponse>
}
