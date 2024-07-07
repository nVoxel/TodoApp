package com.voxeldev.todoapp.task.di

import android.content.Context
import com.voxeldev.todoapp.api.repository.PreferencesRepository
import com.voxeldev.todoapp.api.repository.TodoItemListRepository
import com.voxeldev.todoapp.api.repository.TodoItemRepository
import com.voxeldev.todoapp.utils.platform.NetworkObserver
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider

/**
 * @author nvoxel
 */
interface TaskFeatureDependencies {

    val applicationContext: Context

    val todoItemListRepository: TodoItemListRepository
    val todoItemRepository: TodoItemRepository
    val preferencesRepository: PreferencesRepository
    val networkObserver: NetworkObserver
    val coroutineDispatcherProvider: CoroutineDispatcherProvider
}
