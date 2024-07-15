package com.voxeldev.todoapp.di.dependencies

import android.content.Context
import com.voxeldev.todoapp.api.repository.AuthTokenRepository
import com.voxeldev.todoapp.api.repository.PreferencesRepository
import com.voxeldev.todoapp.api.repository.TodoItemRepository
import com.voxeldev.todoapp.settings.di.SettingsFeatureDependencies
import com.voxeldev.todoapp.utils.platform.NetworkObserver
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import dagger.Module
import dagger.Provides

/**
 * @author nvoxel
 */
@Module
class SettingsFeatureDependenciesModule {

    @Provides
    fun provideSettingsFeatureDependencies(
        applicationContext: Context,
        authTokenRepository: AuthTokenRepository,
        preferencesRepository: PreferencesRepository,
        todoItemRepository: TodoItemRepository,
        networkObserver: NetworkObserver,
        coroutineDispatcherProvider: CoroutineDispatcherProvider,
    ): SettingsFeatureDependencies = SettingsFeatureDependenciesImpl(
        applicationContext = applicationContext,
        authTokenRepository = authTokenRepository,
        preferencesRepository = preferencesRepository,
        todoItemRepository = todoItemRepository,
        networkObserver = networkObserver,
        coroutineDispatcherProvider = coroutineDispatcherProvider,
    )
}

internal class SettingsFeatureDependenciesImpl(
    override val applicationContext: Context,
    override val authTokenRepository: AuthTokenRepository,
    override val preferencesRepository: PreferencesRepository,
    override val todoItemRepository: TodoItemRepository,
    override val networkObserver: NetworkObserver,
    override val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : SettingsFeatureDependencies
