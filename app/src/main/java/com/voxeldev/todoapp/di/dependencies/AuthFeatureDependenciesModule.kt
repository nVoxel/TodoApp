package com.voxeldev.todoapp.di.dependencies

import android.content.Context
import com.voxeldev.todoapp.auth.di.AuthFeatureDependencies
import com.voxeldev.todoapp.domain.usecase.todoitem.GetAllTodoItemsFlowUseCase
import com.voxeldev.todoapp.domain.usecase.token.ClearAuthTokenUseCase
import com.voxeldev.todoapp.domain.usecase.token.SetAuthTokenUseCase
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
        setAuthTokenUseCase: SetAuthTokenUseCase,
        clearAuthTokenUseCase: ClearAuthTokenUseCase,
        getAllTodoItemsFlowUseCase: GetAllTodoItemsFlowUseCase,
        networkObserver: NetworkObserver,
        coroutineDispatcherProvider: CoroutineDispatcherProvider,
    ): AuthFeatureDependencies = AuthFeatureDependenciesImpl(
        applicationContext = applicationContext,
        setAuthTokenUseCase = setAuthTokenUseCase,
        clearAuthTokenUseCase = clearAuthTokenUseCase,
        getAllTodoItemsFlowUseCase = getAllTodoItemsFlowUseCase,
        networkObserver = networkObserver,
        coroutineDispatcherProvider = coroutineDispatcherProvider,
    )
}

internal class AuthFeatureDependenciesImpl(
    override val applicationContext: Context,
    override val setAuthTokenUseCase: SetAuthTokenUseCase,
    override val clearAuthTokenUseCase: ClearAuthTokenUseCase,
    override val getAllTodoItemsFlowUseCase: GetAllTodoItemsFlowUseCase,
    override val networkObserver: NetworkObserver,
    override val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : AuthFeatureDependencies
