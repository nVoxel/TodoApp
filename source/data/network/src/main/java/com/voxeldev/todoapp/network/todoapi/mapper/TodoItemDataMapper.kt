package com.voxeldev.todoapp.network.todoapi.mapper

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.network.mapper.ResponseMapper
import com.voxeldev.todoapp.network.todoapi.datasource.TodoItemData
import com.voxeldev.todoapp.network.todoapi.mapper.converters.toImportance
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author nvoxel
 */
@Singleton
internal class TodoItemDataMapper @Inject constructor() : ResponseMapper<TodoItemData, TodoItem> {

    override fun toModel(response: TodoItemData): TodoItem =
        TodoItem(
            id = response.id,
            text = response.text,
            importance = response.importance.toImportance(),
            deadlineTimestamp = response.deadlineTimestamp,
            isComplete = response.isComplete,
            creationTimestamp = response.creationTimestamp,
            modifiedTimestamp = response.modifiedTimestamp,
            lastUpdatedBy = response.lastUpdatedBy,
        )
}
