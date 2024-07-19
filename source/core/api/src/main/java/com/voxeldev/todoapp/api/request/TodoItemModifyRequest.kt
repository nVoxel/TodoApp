package com.voxeldev.todoapp.api.request

import com.voxeldev.todoapp.api.model.TodoItemImportance

/**
 * Request that is used to create or update task.
 * @author nvoxel
 */
data class TodoItemModifyRequest(
    val id: String,
    val text: String,
    val importance: TodoItemImportance,
    val deadlineTimestamp: Long? = null,
    val isComplete: Boolean,
    val creationTimestamp: Long,
    val modifiedTimestamp: Long,
)
