package com.voxeldev.todoapp.auth.di

import android.content.Context
import com.voxeldev.todoapp.domain.usecase.todoitem.GetAllTodoItemsFlowUseCase
import com.voxeldev.todoapp.domain.usecase.token.ClearAuthTokenUseCase
import com.voxeldev.todoapp.domain.usecase.token.SetAuthTokenUseCase
import com.voxeldev.todoapp.utils.platform.NetworkObserver
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider

/**
 * @author nvoxel
 */
interface AuthFeatureDependencies {

    val applicationContext: Context

    val setAuthTokenUseCase: SetAuthTokenUseCase
    val clearAuthTokenUseCase: ClearAuthTokenUseCase
    val getAllTodoItemsFlowUseCase: GetAllTodoItemsFlowUseCase
    val networkObserver: NetworkObserver
    val coroutineDispatcherProvider: CoroutineDispatcherProvider
}
