package com.voxeldev.todoapp.auth.di

import android.content.Context
import com.voxeldev.todoapp.api.repository.AuthTokenRepository
import com.voxeldev.todoapp.api.repository.TodoItemRepository
import com.voxeldev.todoapp.utils.platform.NetworkObserver
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider

/**
 * @author nvoxel
 */
interface AuthFeatureDependencies {

    val applicationContext: Context

    val authTokenRepository: AuthTokenRepository
    val todoItemRepository: TodoItemRepository
    val networkObserver: NetworkObserver
    val coroutineDispatcherProvider: CoroutineDispatcherProvider
}
