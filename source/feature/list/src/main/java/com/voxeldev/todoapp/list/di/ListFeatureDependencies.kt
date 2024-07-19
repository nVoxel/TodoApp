package com.voxeldev.todoapp.list.di

import android.content.Context
import com.voxeldev.todoapp.api.repository.PreferencesRepository
import com.voxeldev.todoapp.api.repository.TodoItemRepository
import com.voxeldev.todoapp.utils.platform.NetworkObserver
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider

/**
 * @author nvoxel
 */
interface ListFeatureDependencies {

    val applicationContext: Context

    val todoItemRepository: TodoItemRepository
    val preferencesRepository: PreferencesRepository
    val networkObserver: NetworkObserver
    val coroutineDispatcherProvider: CoroutineDispatcherProvider
}
