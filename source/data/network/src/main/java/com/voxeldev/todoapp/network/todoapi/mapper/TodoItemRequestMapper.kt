package com.voxeldev.todoapp.network.todoapi.mapper

import com.voxeldev.todoapp.api.request.TodoItemModifyRequest
import com.voxeldev.todoapp.network.mapper.RequestMapper
import com.voxeldev.todoapp.network.todoapi.datasource.TodoItemData
import com.voxeldev.todoapp.network.todoapi.datasource.request.TodoSingleRequest
import com.voxeldev.todoapp.network.todoapi.mapper.converters.toRequest
import javax.inject.Inject

/**
 * Maps [TodoItemModifyRequest] model to [TodoSingleRequest] request.
 * @author nvoxel
 */
internal class TodoItemRequestMapper @Inject constructor() : RequestMapper<TodoItemModifyRequest, TodoSingleRequest> {

    override fun toRequest(model: TodoItemModifyRequest): TodoSingleRequest =
        TodoSingleRequest(
            element = TodoItemData(
                id = model.todoItem.id,
                text = model.todoItem.text,
                importance = model.todoItem.importance.toRequest(),
                deadlineTimestamp = model.todoItem.deadlineTimestamp,
                isComplete = model.todoItem.isComplete,
                creationTimestamp = model.todoItem.creationTimestamp,
                modifiedTimestamp = model.todoItem.modifiedTimestamp,
                lastUpdatedBy = model.deviceId,
            ),
        )
}
