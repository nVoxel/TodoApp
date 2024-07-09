package com.voxeldev.todoapp.repository.mapper

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.network.todoapi.datasource.TodoItemData
import com.voxeldev.todoapp.network.todoapi.mapper.converters.toImportance
import javax.inject.Inject

/**
 * @author nvoxel
 */
class TodoItemRemoteMapper @Inject constructor() {

    fun toModel(todoItemData: TodoItemData): TodoItem = TodoItem(
        id = todoItemData.id,
        text = todoItemData.text,
        importance = todoItemData.importance.toImportance(),
        deadlineTimestamp = todoItemData.deadlineTimestamp,
        isComplete = todoItemData.isComplete,
        creationTimestamp = todoItemData.creationTimestamp,
        modifiedTimestamp = todoItemData.modifiedTimestamp,
        lastUpdatedBy = todoItemData.lastUpdatedBy,
    )
}
