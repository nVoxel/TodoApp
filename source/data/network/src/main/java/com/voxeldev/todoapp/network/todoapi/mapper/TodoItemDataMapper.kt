package com.voxeldev.todoapp.network.todoapi.mapper

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.api.model.TodoItemImportance
import com.voxeldev.todoapp.network.mapper.TwoWayMapper
import com.voxeldev.todoapp.network.todoapi.datasource.TodoItemData
import com.voxeldev.todoapp.utils.exceptions.InvalidFormatException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author nvoxel
 */
@Singleton
internal class TodoItemDataMapper @Inject constructor() : TwoWayMapper<TodoItem, TodoItemData, TodoItemData> {

    override fun toRequest(model: TodoItem): TodoItemData =
        TodoItemData(
            id = model.id,
            text = model.text,
            importance = model.importance.toRequest(),
            deadlineTimestamp = model.deadlineTimestamp,
            isComplete = model.isComplete,
            creationTimestamp = model.creationTimestamp,
            modifiedTimestamp = model.modifiedTimestamp,
            lastUpdatedBy = "TODO", // TODO
        )

    override fun toModel(response: TodoItemData): TodoItem =
        TodoItem(
            id = response.id,
            text = response.text,
            importance = response.importance.toImportance(),
            deadlineTimestamp = response.deadlineTimestamp,
            isComplete = response.isComplete,
            creationTimestamp = response.creationTimestamp,
            modifiedTimestamp = response.modifiedTimestamp,
        )

    private fun TodoItemImportance.toRequest(): String =
        when (this) {
            TodoItemImportance.Low -> IMPORTANCE_LOW
            TodoItemImportance.Normal -> IMPORTANCE_NORMAL
            TodoItemImportance.Urgent -> IMPORTANCE_URGENT
        }

    private fun String.toImportance(): TodoItemImportance =
        when (this) {
            IMPORTANCE_LOW -> TodoItemImportance.Low
            IMPORTANCE_NORMAL -> TodoItemImportance.Normal
            IMPORTANCE_URGENT -> TodoItemImportance.Urgent
            else -> throw InvalidFormatException()
        }

    private companion object {
        const val IMPORTANCE_LOW = "low"
        const val IMPORTANCE_NORMAL = "basic"
        const val IMPORTANCE_URGENT = "important"
    }
}
