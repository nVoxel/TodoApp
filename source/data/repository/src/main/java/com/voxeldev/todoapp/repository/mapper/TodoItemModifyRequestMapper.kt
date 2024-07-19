package com.voxeldev.todoapp.repository.mapper

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.api.request.TodoItemModifyRequest
import javax.inject.Inject

/**
 * @author nvoxel
 */
internal class TodoItemModifyRequestMapper @Inject constructor() {

    fun toModel(request: TodoItemModifyRequest, deviceId: String): TodoItem = TodoItem(
        id = request.id,
        text = request.text,
        importance = request.importance,
        deadlineTimestamp = request.deadlineTimestamp,
        isComplete = request.isComplete,
        creationTimestamp = request.creationTimestamp,
        modifiedTimestamp = request.modifiedTimestamp,
        lastUpdatedBy = deviceId,
    )
}
