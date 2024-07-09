package com.voxeldev.todoapp.work

import androidx.work.DelegatingWorkerFactory
import com.voxeldev.todoapp.api.repository.TodoItemRepository
import com.voxeldev.todoapp.settings.work.AutoRefreshWorkerFactory
import com.voxeldev.todoapp.utils.di.scopes.AppScope
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import javax.inject.Inject

/**
 * @author nvoxel
 */
@AppScope
class TodoAppWorkerFactory @Inject constructor(
    todoItemRepository: TodoItemRepository,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : DelegatingWorkerFactory() {

    init {
        addFactory(
            AutoRefreshWorkerFactory(
                todoItemRepository = todoItemRepository,
                coroutineDispatcherProvider = coroutineDispatcherProvider,
            ),
        )
    }
}
