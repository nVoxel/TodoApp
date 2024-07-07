package com.voxeldev.todoapp.di.dependencies

import android.content.Context
import com.voxeldev.todoapp.api.repository.PreferencesRepository
import com.voxeldev.todoapp.api.repository.TodoItemListRepository
import com.voxeldev.todoapp.api.repository.TodoItemRepository
import com.voxeldev.todoapp.task.di.TaskFeatureDependencies
import com.voxeldev.todoapp.utils.platform.NetworkObserver
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import dagger.Module
import dagger.Provides

/**
 * @author nvoxel
 */
@Module
class TaskFeatureDependenciesModule {

    @Provides
    fun provideTaskFeatureDependencies(
        applicationContext: Context,
        todoItemListRepository: TodoItemListRepository,
        todoItemRepository: TodoItemRepository,
        preferencesRepository: PreferencesRepository,
        networkObserver: NetworkObserver,
        coroutineDispatcherProvider: CoroutineDispatcherProvider,
    ): TaskFeatureDependencies = TaskFeatureDependenciesImpl(
        applicationContext = applicationContext,
        todoItemListRepository = todoItemListRepository,
        todoItemRepository = todoItemRepository,
        preferencesRepository = preferencesRepository,
        networkObserver = networkObserver,
        coroutineDispatcherProvider = coroutineDispatcherProvider,
    )
}

internal class TaskFeatureDependenciesImpl(
    override val applicationContext: Context,
    override val todoItemListRepository: TodoItemListRepository,
    override val todoItemRepository: TodoItemRepository,
    override val preferencesRepository: PreferencesRepository,
    override val networkObserver: NetworkObserver,
    override val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : TaskFeatureDependencies
