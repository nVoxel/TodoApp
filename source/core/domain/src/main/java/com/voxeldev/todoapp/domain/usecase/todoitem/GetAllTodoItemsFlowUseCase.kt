package com.voxeldev.todoapp.domain.usecase.todoitem

import com.voxeldev.todoapp.api.model.TodoItemList
import com.voxeldev.todoapp.api.repository.TodoItemRepository
import com.voxeldev.todoapp.domain.usecase.base.BaseUseCase
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * Gets [Flow] of [TodoItemList].
 * @author nvoxel
 */
class GetAllTodoItemsFlowUseCase @Inject constructor(
    private val todoItemRepository: TodoItemRepository,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : BaseUseCase<BaseUseCase.NoParams, StateFlow<TodoItemList>>(
    coroutineDispatcherProvider = coroutineDispatcherProvider,
) {

    override suspend fun run(params: NoParams): Result<StateFlow<TodoItemList>> =
        todoItemRepository.getAllFlow()
}
