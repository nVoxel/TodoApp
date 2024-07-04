package com.voxeldev.todoapp.api.request

import com.voxeldev.todoapp.api.model.TodoItem

/**
 * @author nvoxel
 */
data class TodoItemModifyRequest(
    val todoItem: TodoItem,
    val revision: Int,
    val deviceId: String,
)
