package com.voxeldev.todoapp.domain.usecase.todoitem

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.api.repository.TodoItemRepository
import com.voxeldev.todoapp.domain.usecase.base.BaseUseCase
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import javax.inject.Inject

/**
 * @author nvoxel
 */
class GetSingleTodoItemUseCase @Inject constructor(
    private val todoItemRepository: TodoItemRepository,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : BaseUseCase<String, TodoItem>(coroutineDispatcherProvider = coroutineDispatcherProvider) {

    override suspend fun run(params: String): Result<TodoItem> =
        todoItemRepository.getSingle(id = params)
}
