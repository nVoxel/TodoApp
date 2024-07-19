package com.voxeldev.todoapp.database.todo.datasource

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.database.todo.TodoDao
import com.voxeldev.todoapp.database.todo.TodoEntityList
import com.voxeldev.todoapp.database.todo.mapper.TodoItemEntityMapper
import javax.inject.Inject

/**
 * @author nvoxel
 */
internal class DefaultTodoItemLocalDataSource @Inject constructor(
    private val todoDao: TodoDao,
    private val todoItemEntityMapper: TodoItemEntityMapper,
) : TodoItemLocalDataSource {

    override suspend fun getAllAsList(): Result<TodoEntityList> = runCatching {
        todoDao.getAllTodosAsList()
    }

    override suspend fun insert(todoItem: TodoItem, revision: Int): Result<Long> = runCatching {
        todoDao.insertTodo(
            todoEntity = todoItemEntityMapper.toEntity(
                model = todoItem,
                revision = revision,
            ),
        )
    }

    override suspend fun update(todoItem: TodoItem, revision: Int): Result<Unit> = runCatching {
        todoDao.updateTodo(
            id = todoItem.id,
            text = todoItem.text,
            importance = todoItem.importance,
            deadline = todoItem.deadlineTimestamp,
            isComplete = todoItem.isComplete,
            creationTimestamp = todoItem.creationTimestamp,
            modifiedTimestamp = todoItem.modifiedTimestamp,
            lastUpdatedBy = todoItem.lastUpdatedBy,
            revision = revision,
        )
    }

    override suspend fun setShouldBeCreated(id: String, shouldBeCreated: Boolean): Result<Unit> = runCatching {
        todoDao.setItemShouldBeCreated(id = id, shouldBeCreated = shouldBeCreated)
    }

    override suspend fun setShouldBeUpdated(id: String, shouldBeUpdated: Boolean): Result<Unit> = runCatching {
        todoDao.setItemShouldBeUpdated(id = id, shouldBeUpdated = shouldBeUpdated)
    }

    override suspend fun setShouldBeDeleted(id: String, shouldBeDeleted: Boolean): Result<Unit> = runCatching {
        todoDao.setItemShouldBeDeleted(id = id, shouldBeDeleted = shouldBeDeleted)
    }

    override suspend fun deleteById(id: String): Result<Unit> = runCatching {
        todoDao.deleteSingleTodo(id = id)
    }

    override suspend fun clearTable(): Result<Unit> = runCatching {
        todoDao.clearTable()
    }
}
