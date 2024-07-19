package com.voxeldev.todoapp.network.todoapi.mapper

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.network.todoapi.datasource.TodoItemData
import com.voxeldev.todoapp.network.todoapi.mapper.converters.toRequest
import javax.inject.Inject

/**
* @author nvoxel
*/
internal class TodoItemDataMapper @Inject constructor() {

    fun toRequest(model: TodoItem): TodoItemData = TodoItemData(
        id = model.id,
        text = model.text,
        importance = model.importance.toRequest(),
        deadlineTimestamp = model.deadlineTimestamp,
        isComplete = model.isComplete,
        creationTimestamp = model.creationTimestamp,
        modifiedTimestamp = model.modifiedTimestamp,
        lastUpdatedBy = model.lastUpdatedBy,
    )
}
