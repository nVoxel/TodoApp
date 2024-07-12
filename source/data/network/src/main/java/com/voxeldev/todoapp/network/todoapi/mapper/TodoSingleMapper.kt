package com.voxeldev.todoapp.network.todoapi.mapper

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.network.mapper.ResponseMapper
import com.voxeldev.todoapp.network.todoapi.datasource.response.TodoSingleResponse
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Maps [TodoSingleResponse] response to [TodoItem] model.
 * @author nvoxel
 */
@Singleton
internal class TodoSingleMapper @Inject constructor(
    private val todoItemDataMapper: TodoItemDataMapper,
) : ResponseMapper<TodoSingleResponse, TodoItem> {

    override fun toModel(response: TodoSingleResponse): TodoItem =
        todoItemDataMapper.toModel(response = response.element)
}
