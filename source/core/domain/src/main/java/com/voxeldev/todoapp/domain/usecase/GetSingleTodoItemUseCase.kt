package com.voxeldev.todoapp.domain.usecase

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.api.repository.TodoItemsRepository
import com.voxeldev.todoapp.domain.usecase.base.BaseUseCase
import javax.inject.Inject

/**
 * @author nvoxel
 */
class GetSingleTodoItemUseCase @Inject constructor(
    private val todoItemsRepository: TodoItemsRepository,
) : BaseUseCase<String, TodoItem>() {

    override fun run(params: String): Result<TodoItem> =
        todoItemsRepository.getSingle(id = params)
}
