package com.voxeldev.todoapp.api.model

/**
 * @author nvoxel
 */
data class TodoItemList(
    val list: List<TodoItem>,
    val isOffline: Boolean,
)
