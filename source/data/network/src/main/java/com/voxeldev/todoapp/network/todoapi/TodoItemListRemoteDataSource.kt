package com.voxeldev.todoapp.network.todoapi

import com.voxeldev.todoapp.network.todoapi.datasource.response.TodoListResponse

/**
 * Provides task list as well as the current data revision.
 * @author nvoxel
 */
interface TodoItemListRemoteDataSource {

    suspend fun getAll(): Result<TodoListResponse>
}
