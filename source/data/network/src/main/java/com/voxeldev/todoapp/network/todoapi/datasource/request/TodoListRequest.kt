package com.voxeldev.todoapp.network.todoapi.datasource.request

import com.voxeldev.todoapp.network.todoapi.datasource.TodoItemData
import kotlinx.serialization.Serializable

/**
 * @author nvoxel
 */
@Serializable
internal data class TodoListRequest(
    val list: List<TodoItemData>,
)
