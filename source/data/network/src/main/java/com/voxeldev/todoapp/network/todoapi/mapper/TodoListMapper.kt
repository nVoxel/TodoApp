package com.voxeldev.todoapp.network.todoapi.mapper

import com.voxeldev.todoapp.api.model.TodoItemList
import com.voxeldev.todoapp.network.mapper.ResponseMapper
import com.voxeldev.todoapp.network.todoapi.datasource.response.TodoListResponse
import javax.inject.Inject

/**
 * Maps [TodoListResponse] response to [TodoItemList] model.
 * @author nvoxel
 */
internal class TodoListMapper @Inject constructor(
    private val todoItemDataMapper: TodoItemDataMapper,
) : ResponseMapper<TodoListResponse, TodoItemList> {

    override fun toModel(response: TodoListResponse): TodoItemList =
        response.list.map { itemData -> todoItemDataMapper.toModel(response = itemData) }
}
