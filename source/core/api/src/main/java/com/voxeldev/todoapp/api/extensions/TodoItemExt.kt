package com.voxeldev.todoapp.api.extensions

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.api.request.TodoItemModifyRequest

/**
 * @author nvoxel
 */
fun TodoItem.toModifyRequest(): TodoItemModifyRequest = TodoItemModifyRequest(
    id = id,
    text = text,
    importance = importance,
    deadlineTimestamp = deadlineTimestamp,
    isComplete = isComplete,
    creationTimestamp = creationTimestamp,
    modifiedTimestamp = modifiedTimestamp,
)
