package com.voxeldev.todoapp.database.todo.mapper

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.database.todo.TodoEntity
import javax.inject.Inject

/**
 * @author nvoxel
 */
internal class TodoItemEntityMapper @Inject constructor() {

    fun toEntity(model: TodoItem, revision: Int): TodoEntity =
        TodoEntity(
            id = model.id,
            text = model.text,
            importance = model.importance,
            deadlineTimestamp = model.deadlineTimestamp,
            isComplete = model.isComplete,
            creationTimestamp = model.creationTimestamp,
            modifiedTimestamp = model.modifiedTimestamp,
            lastUpdatedBy = model.lastUpdatedBy,
            revision = revision,
        )
}
