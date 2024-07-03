package com.voxeldev.todoapp.network.todoapi.datasource.response

import com.voxeldev.todoapp.network.todoapi.datasource.TodoItemData
import kotlinx.serialization.Serializable

/**
 * @author nvoxel
 */
@Serializable
internal data class TodoSingleResponse(
    val status: String,
    val element: TodoItemData,
    val revision: Int,
)
