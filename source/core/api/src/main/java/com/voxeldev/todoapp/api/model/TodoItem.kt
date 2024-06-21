package com.voxeldev.todoapp.api.model

/**
 * @author nvoxel
 */
data class TodoItem(
    val id: String,
    val text: String,
    val importance: TodoItemImportance,
    val deadlineTimestamp: Long?,
    val isComplete: Boolean,
    val creationTimestamp: Long,
    val modifiedTimestamp: Long?,
)
