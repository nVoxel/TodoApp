package com.voxeldev.todoapp.network.todoapi.mapper

import com.voxeldev.todoapp.api.request.TodoItemModifyRequest
import com.voxeldev.todoapp.network.todoapi.datasource.TodoItemData
import com.voxeldev.todoapp.network.todoapi.datasource.request.TodoSingleRequest
import com.voxeldev.todoapp.network.todoapi.mapper.converters.toRequest
import javax.inject.Inject

/**
 * Maps [TodoItemModifyRequest] model to [TodoSingleRequest] request.
 * @author nvoxel
 */
internal class TodoItemRequestMapper @Inject constructor() {

    fun toRequest(model: TodoItemModifyRequest, deviceId: String): TodoSingleRequest =
        TodoSingleRequest(
            element = TodoItemData(
                id = model.id,
                text = model.text,
                importance = model.importance.toRequest(),
                deadlineTimestamp = model.deadlineTimestamp,
                isComplete = model.isComplete,
                creationTimestamp = model.creationTimestamp,
                modifiedTimestamp = model.modifiedTimestamp,
                lastUpdatedBy = deviceId,
            ),
        )
}
