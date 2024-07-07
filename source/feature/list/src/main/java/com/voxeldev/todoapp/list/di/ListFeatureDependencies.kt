package com.voxeldev.todoapp.list.di

import android.content.Context
import com.voxeldev.todoapp.domain.usecase.todoitem.DeleteTodoItemUseCase
import com.voxeldev.todoapp.domain.usecase.todoitem.GetAllTodoItemsFlowUseCase
import com.voxeldev.todoapp.domain.usecase.todoitem.UpdateTodoItemUseCase
import com.voxeldev.todoapp.utils.platform.NetworkObserver
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider

/**
 * @author nvoxel
 */
interface ListFeatureDependencies {

    val applicationContext: Context

    val getAllTodoItemsFlowUseCase: GetAllTodoItemsFlowUseCase
    val updateTodoItemUseCase: UpdateTodoItemUseCase
    val deleteTodoItemUseCase: DeleteTodoItemUseCase
    val networkObserver: NetworkObserver
    val coroutineDispatcherProvider: CoroutineDispatcherProvider
}
