package com.voxeldev.todoapp.repository.extensions

import com.voxeldev.todoapp.api.request.TodoItemModifyRequest
import com.voxeldev.todoapp.database.todo.TodoEntity

/**
 * @author nvoxel
 */
fun TodoEntity.toModifyRequest(): TodoItemModifyRequest = TodoItemModifyRequest(
    id = id,
    text = text,
    importance = importance,
    deadlineTimestamp = deadlineTimestamp,
    isComplete = isComplete,
    creationTimestamp = creationTimestamp,
    modifiedTimestamp = modifiedTimestamp,
)
