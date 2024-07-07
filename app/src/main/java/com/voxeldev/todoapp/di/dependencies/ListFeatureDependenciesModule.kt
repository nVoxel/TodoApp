package com.voxeldev.todoapp.di.dependencies

import android.content.Context
import com.voxeldev.todoapp.domain.usecase.todoitem.DeleteTodoItemUseCase
import com.voxeldev.todoapp.domain.usecase.todoitem.GetAllTodoItemsFlowUseCase
import com.voxeldev.todoapp.domain.usecase.todoitem.UpdateTodoItemUseCase
import com.voxeldev.todoapp.list.di.ListFeatureDependencies
import com.voxeldev.todoapp.utils.platform.NetworkObserver
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import dagger.Module
import dagger.Provides

/**
 * @author nvoxel
 */
@Module
class ListFeatureDependenciesModule {

    @Provides
    fun provideListFeatureDependencies(
        applicationContext: Context,
        getAllTodoItemsFlowUseCase: GetAllTodoItemsFlowUseCase,
        updateTodoItemUseCase: UpdateTodoItemUseCase,
        deleteTodoItemUseCase: DeleteTodoItemUseCase,
        networkObserver: NetworkObserver,
        coroutineDispatcherProvider: CoroutineDispatcherProvider,
    ): ListFeatureDependencies = ListFeatureDependenciesImpl(
        applicationContext = applicationContext,
        getAllTodoItemsFlowUseCase = getAllTodoItemsFlowUseCase,
        updateTodoItemUseCase = updateTodoItemUseCase,
        deleteTodoItemUseCase = deleteTodoItemUseCase,
        networkObserver = networkObserver,
        coroutineDispatcherProvider = coroutineDispatcherProvider,
    )
}

internal class ListFeatureDependenciesImpl(
    override val applicationContext: Context,
    override val getAllTodoItemsFlowUseCase: GetAllTodoItemsFlowUseCase,
    override val updateTodoItemUseCase: UpdateTodoItemUseCase,
    override val deleteTodoItemUseCase: DeleteTodoItemUseCase,
    override val networkObserver: NetworkObserver,
    override val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : ListFeatureDependencies
