package com.voxeldev.todoapp.domain.usecase.todoitem

import com.voxeldev.todoapp.api.model.TodoItemList
import com.voxeldev.todoapp.api.repository.TodoItemListRepository
import com.voxeldev.todoapp.domain.usecase.base.BaseUseCase
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Gets [Flow] of [TodoItemList].
 * @author nvoxel
 */
class GetAllTodoItemsFlowUseCase @Inject constructor(
    private val todoItemsRepository: TodoItemListRepository,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : BaseUseCase<BaseUseCase.NoParams, Flow<TodoItemList>>(coroutineDispatcherProvider = coroutineDispatcherProvider) {

    override suspend fun run(params: NoParams): Result<Flow<TodoItemList>> =
        todoItemsRepository.getAllFlow()
}
