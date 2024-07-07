package com.voxeldev.todoapp.task.di

import android.content.Context
import com.voxeldev.todoapp.domain.usecase.todoitem.CreateTodoItemUseCase
import com.voxeldev.todoapp.domain.usecase.todoitem.DeleteTodoItemUseCase
import com.voxeldev.todoapp.domain.usecase.todoitem.GetSingleTodoItemUseCase
import com.voxeldev.todoapp.domain.usecase.todoitem.UpdateTodoItemUseCase
import com.voxeldev.todoapp.utils.platform.NetworkObserver
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider

/**
 * @author nvoxel
 */
interface TaskFeatureDependencies {

    val applicationContext: Context

    val createTodoItemUseCase: CreateTodoItemUseCase
    val deleteTodoItemUseCase: DeleteTodoItemUseCase
    val getSingleTodoItemUseCase: GetSingleTodoItemUseCase
    val updateTodoItemUseCase: UpdateTodoItemUseCase
    val networkObserver: NetworkObserver
    val coroutineDispatcherProvider: CoroutineDispatcherProvider
}
