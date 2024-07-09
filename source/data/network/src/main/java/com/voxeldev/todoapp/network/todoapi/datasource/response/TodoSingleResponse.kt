package com.voxeldev.todoapp.network.todoapi.datasource.response

import com.voxeldev.todoapp.network.todoapi.datasource.TodoItemData
import kotlinx.serialization.Serializable

/**
 * Single task response from the backend.
 * @author nvoxel
 */
@Serializable
data class TodoSingleResponse(
    val status: String,
    val element: TodoItemData,
    val revision: Int,
)
