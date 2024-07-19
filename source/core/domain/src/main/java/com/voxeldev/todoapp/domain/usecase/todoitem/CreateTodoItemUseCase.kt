package com.voxeldev.todoapp.domain.usecase.todoitem

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.api.repository.TodoItemRepository
import com.voxeldev.todoapp.api.request.TodoItemModifyRequest
import com.voxeldev.todoapp.domain.usecase.base.BaseUseCase
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import javax.inject.Inject

/**
 * Creates new [TodoItem].
 * @author nvoxel
 */
class CreateTodoItemUseCase @Inject constructor(
    private val todoItemRepository: TodoItemRepository,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : BaseUseCase<TodoItemModifyRequest, Unit>(coroutineDispatcherProvider = coroutineDispatcherProvider) {

    override suspend fun run(params: TodoItemModifyRequest): Result<Unit> =
        todoItemRepository.createItem(request = params)
}
