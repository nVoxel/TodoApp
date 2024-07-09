package com.voxeldev.todoapp.network.todoapi.datasource.response

import com.voxeldev.todoapp.network.todoapi.datasource.TodoItemData
import kotlinx.serialization.Serializable

/**
 * Task list response from the backend.
 * @author nvoxel
 */
@Serializable
data class TodoListResponse(
    val status: String,
    val list: List<TodoItemData>,
    val revision: Int,
)
