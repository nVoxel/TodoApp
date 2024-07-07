package com.voxeldev.todoapp.di.dependencies

import android.content.Context
import com.voxeldev.todoapp.api.repository.AuthTokenRepository
import com.voxeldev.todoapp.api.repository.TodoItemListRepository
import com.voxeldev.todoapp.auth.di.AuthFeatureDependencies
import com.voxeldev.todoapp.utils.platform.NetworkObserver
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import dagger.Module
import dagger.Provides

/**
 * @author nvoxel
 */
@Module
class AuthFeatureDependenciesModule {

    @Provides
    fun provideAuthFeatureDependencies(
        applicationContext: Context,
        authTokenRepository: AuthTokenRepository,
        todoItemListRepository: TodoItemListRepository,
        networkObserver: NetworkObserver,
        coroutineDispatcherProvider: CoroutineDispatcherProvider,
    ): AuthFeatureDependencies = AuthFeatureDependenciesImpl(
        applicationContext = applicationContext,
        authTokenRepository = authTokenRepository,
        todoItemListRepository = todoItemListRepository,
        networkObserver = networkObserver,
        coroutineDispatcherProvider = coroutineDispatcherProvider,
    )
}

internal class AuthFeatureDependenciesImpl(
    override val applicationContext: Context,
    override val authTokenRepository: AuthTokenRepository,
    override val todoItemListRepository: TodoItemListRepository,
    override val networkObserver: NetworkObserver,
    override val coroutineDispatcherProvider: CoroutineDispatcherProvider,

    ) : AuthFeatureDependencies
