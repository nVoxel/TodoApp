package com.voxeldev.todoapp.api.request

import com.voxeldev.todoapp.api.model.TodoItem

/**
 * Request that is used to create or update task.
 * @author nvoxel
 */
data class TodoItemModifyRequest(
    val todoItem: TodoItem,
    val revision: Int,
    val deviceId: String,
)
