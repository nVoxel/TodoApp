package com.voxeldev.todoapp.domain.usecase

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.api.repository.TodoItemsRepository
import com.voxeldev.todoapp.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author nvoxel
 */
class GetAllTodoItemsFlowUseCase @Inject constructor(
    private val todoItemsRepository: TodoItemsRepository,
) : BaseUseCase<BaseUseCase.NoParams, Flow<List<TodoItem>>>() {

    override fun run(params: NoParams): Result<Flow<List<TodoItem>>> =
        todoItemsRepository.getAllFlow()
}
