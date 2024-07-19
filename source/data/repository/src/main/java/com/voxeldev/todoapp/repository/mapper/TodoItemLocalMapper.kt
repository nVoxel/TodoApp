package com.voxeldev.todoapp.repository.mapper

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.database.todo.TodoEntity
import javax.inject.Inject

/**
 * @author nvoxel
 */
class TodoItemLocalMapper @Inject constructor() {

    fun toModel(todoEntity: TodoEntity): TodoItem = TodoItem(
        id = todoEntity.id,
        text = todoEntity.text,
        importance = todoEntity.importance,
        deadlineTimestamp = todoEntity.deadlineTimestamp,
        isComplete = todoEntity.isComplete,
        creationTimestamp = todoEntity.creationTimestamp,
        modifiedTimestamp = todoEntity.modifiedTimestamp,
        lastUpdatedBy = todoEntity.lastUpdatedBy,
    )
}
