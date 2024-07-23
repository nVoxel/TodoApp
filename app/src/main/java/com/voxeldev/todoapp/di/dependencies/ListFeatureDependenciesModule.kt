package com.voxeldev.todoapp.di.dependencies

import android.content.Context
import com.voxeldev.todoapp.api.repository.PreferencesRepository
import com.voxeldev.todoapp.api.repository.TodoItemRepository
import com.voxeldev.todoapp.list.di.ListFeatureDependencies
import com.voxeldev.todoapp.utils.platform.NetworkObserver
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import dagger.Module
import dagger.Provides

/**
 * @author nvoxel
 */
@Module
internal class ListFeatureDependenciesModule {

    @Provides
    fun provideListFeatureDependencies(
        applicationContext: Context,
        todoItemRepository: TodoItemRepository,
        preferencesRepository: PreferencesRepository,
        networkObserver: NetworkObserver,
        coroutineDispatcherProvider: CoroutineDispatcherProvider,
    ): ListFeatureDependencies = ListFeatureDependenciesImpl(
        applicationContext = applicationContext,
        todoItemRepository = todoItemRepository,
        preferencesRepository = preferencesRepository,
        networkObserver = networkObserver,
        coroutineDispatcherProvider = coroutineDispatcherProvider,
    )
}

internal class ListFeatureDependenciesImpl(
    override val applicationContext: Context,
    override val todoItemRepository: TodoItemRepository,
    override val preferencesRepository: PreferencesRepository,
    override val networkObserver: NetworkObserver,
    override val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : ListFeatureDependencies
