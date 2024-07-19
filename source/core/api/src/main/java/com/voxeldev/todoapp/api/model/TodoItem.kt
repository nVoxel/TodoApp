package com.voxeldev.todoapp.api.model

/**
 * User-created task.
 * @author nvoxel
 */
data class TodoItem(
    val id: String,
    val text: String,
    val importance: TodoItemImportance,
    val deadlineTimestamp: Long? = null,
    val isComplete: Boolean,
    val creationTimestamp: Long,
    val modifiedTimestamp: Long,
    val lastUpdatedBy: String,
)
