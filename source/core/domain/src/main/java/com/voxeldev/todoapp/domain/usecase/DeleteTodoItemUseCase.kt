package com.voxeldev.todoapp.domain.usecase

import com.voxeldev.todoapp.api.repository.TodoItemsRepository
import com.voxeldev.todoapp.domain.usecase.base.BaseUseCase
import javax.inject.Inject

/**
 * @author nvoxel
 */
class DeleteTodoItemUseCase @Inject constructor(
    private val todoItemsRepository: TodoItemsRepository,
) : BaseUseCase<String, Unit>() {

    override fun run(params: String): Result<Unit> =
        todoItemsRepository.deleteItem(id = params)
}
