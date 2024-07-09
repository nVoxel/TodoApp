package com.voxeldev.todoapp.repository.utils

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.database.todo.TodoEntityList
import com.voxeldev.todoapp.network.todoapi.datasource.response.TodoListResponse

/**
 * @author nvoxel
 */
internal interface DataMerger {

    suspend fun areContentsTheSame(
        localList: TodoEntityList,
        remoteList: TodoListResponse,
    ): Boolean

    suspend fun mergeLists(
        localList: TodoEntityList,
        remoteList: TodoListResponse,
        deviceId: String,
        onRequestCreateLocalItem: suspend (item: TodoItem) -> Unit,
        onRequestUpdateLocalItem: suspend (item: TodoItem) -> Unit,
        onRequestDeleteLocalItem: suspend (id: String) -> Unit,
        onRequestCreateRemoteItem: suspend (item: TodoItem) -> Unit,
        onRequestUpdateRemoteItem: suspend (item: TodoItem) -> Unit,
        onRequestDeleteRemoteItem: suspend (id: String) -> Unit,
    )
}
