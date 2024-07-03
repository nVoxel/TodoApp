package com.voxeldev.todoapp.network.todoapi.mapper

import com.voxeldev.todoapp.api.model.TodoItemList
import com.voxeldev.todoapp.network.mapper.TwoWayMapper
import com.voxeldev.todoapp.network.todoapi.datasource.request.TodoListRequest
import com.voxeldev.todoapp.network.todoapi.datasource.response.TodoListResponse
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author nvoxel
 */
@Singleton
internal class TodoListMapper @Inject constructor(
    private val todoItemDataMapper: TodoItemDataMapper,
) : TwoWayMapper<TodoItemList, TodoListRequest, TodoListResponse> {

    override fun toRequest(model: TodoItemList): TodoListRequest =
        TodoListRequest(
            list = model.map { item -> todoItemDataMapper.toRequest(model = item) },
        )

    override fun toModel(response: TodoListResponse): TodoItemList =
        response.list.map { itemData -> todoItemDataMapper.toModel(response = itemData) }
}
