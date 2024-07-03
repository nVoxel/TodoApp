package com.voxeldev.todoapp.network.todoapi.datasource.response

import com.voxeldev.todoapp.network.todoapi.datasource.TodoItemData
import kotlinx.serialization.Serializable

/**
 * @author nvoxel
 */
@Serializable
internal data class TodoListResponse(
    val status: String,
    val list: List<TodoItemData>,
    val revision: Int,
)
