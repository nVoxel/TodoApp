package com.voxeldev.todoapp.domain.usecase.todoitem

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.api.repository.TodoItemListRepository
import com.voxeldev.todoapp.api.repository.TodoItemRepository
import com.voxeldev.todoapp.domain.usecase.base.BaseUseCase
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import javax.inject.Inject

/**
 * @author nvoxel
 */
class CreateTodoItemUseCase @Inject constructor(
    private val todoItemRepository: TodoItemRepository,
    private val todoItemListRepository: TodoItemListRepository,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : BaseUseCase<TodoItem, Unit>(coroutineDispatcherProvider = coroutineDispatcherProvider) {

    override suspend fun run(params: TodoItem): Result<Unit> =
        todoItemListRepository.getRevision().fold(
            onSuccess = { revision ->
                todoItemRepository.createItem(
                    item = params,
                    revision = revision,
                ).onSuccess {
                    todoItemListRepository.refreshData()
                }
            },
            onFailure = { exception ->
                Result.failure(exception = exception)
            },
        )
}
