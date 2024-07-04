package com.voxeldev.todoapp.domain.usecase.todoitem

import com.voxeldev.todoapp.api.model.TodoItem
import com.voxeldev.todoapp.api.repository.PreferencesRepository
import com.voxeldev.todoapp.api.repository.TodoItemListRepository
import com.voxeldev.todoapp.api.repository.TodoItemRepository
import com.voxeldev.todoapp.api.request.TodoItemModifyRequest
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import javax.inject.Inject

/**
 * @author nvoxel
 */
class CreateTodoItemUseCase @Inject constructor(
    private val todoItemRepository: TodoItemRepository,
    todoItemListRepository: TodoItemListRepository,
    preferencesRepository: PreferencesRepository,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : ModifyingTodoItemUseCase(
    todoItemListRepository = todoItemListRepository,
    preferencesRepository = preferencesRepository,
    coroutineDispatcherProvider = coroutineDispatcherProvider,
) {

    override suspend fun run(params: TodoItem): Result<Unit> = getRevision(todoItem = params)

    override suspend fun modifyTodoItem(
        todoItemModifyRequest: TodoItemModifyRequest,
        onSuccessCallback: suspend () -> Unit,
    ) = runCatching {
        todoItemRepository.createItem(request = todoItemModifyRequest).fold(
            onSuccess = { onSuccessCallback() },
            onFailure = { exception -> return Result.failure<Unit>(exception = exception) },
        )
    }
}
