package com.voxeldev.todoapp.network.todoapi.mapper

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.network.todoapi.datasource.request.TodoListRequest
import javax.inject.Inject

/**
 * @author nvoxel
 */
internal class TodoItemListMapper @Inject constructor(
    private val todoItemDataMapper: TodoItemDataMapper,
) {

    fun toRequest(model: List<TodoItem>): TodoListRequest = TodoListRequest(
        list = model.map { todoItem -> todoItemDataMapper.toRequest(model = todoItem) },
    )
}
