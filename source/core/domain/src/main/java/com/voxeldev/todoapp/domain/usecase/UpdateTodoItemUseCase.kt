package com.voxeldev.todoapp.domain.usecase

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.api.repository.TodoItemsRepository
import com.voxeldev.todoapp.domain.usecase.base.BaseUseCase
import javax.inject.Inject

/**
 * @author nvoxel
 */
class UpdateTodoItemUseCase @Inject constructor(
    private val todoItemsRepository: TodoItemsRepository,
) : BaseUseCase<TodoItem, Unit>() {

    override fun run(params: TodoItem): Result<Unit> =
        todoItemsRepository.updateItem(item = params)
}
