package com.voxeldev.todoapp.network.todoapi.datasource.request

import com.voxeldev.todoapp.network.todoapi.datasource.TodoItemData
import kotlinx.serialization.Serializable

/**
 * Used to create and update tasks on the backend.
 * @author nvoxel
 */
@Serializable
internal data class TodoSingleRequest(
    val element: TodoItemData,
)
