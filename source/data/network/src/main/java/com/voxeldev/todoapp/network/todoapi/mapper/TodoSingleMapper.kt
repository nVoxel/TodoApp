package com.voxeldev.todoapp.network.todoapi.mapper

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.network.mapper.TwoWayMapper
import com.voxeldev.todoapp.network.todoapi.datasource.request.TodoSingleRequest
import com.voxeldev.todoapp.network.todoapi.datasource.response.TodoSingleResponse
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author nvoxel
 */
@Singleton
internal class TodoSingleMapper @Inject constructor(
    private val todoItemDataMapper: TodoItemDataMapper,
) : TwoWayMapper<TodoItem, TodoSingleRequest, TodoSingleResponse> {

    override fun toRequest(model: TodoItem): TodoSingleRequest =
        TodoSingleRequest(element = todoItemDataMapper.toRequest(model = model))

    override fun toModel(response: TodoSingleResponse): TodoItem =
        todoItemDataMapper.toModel(response = response.element)
}
