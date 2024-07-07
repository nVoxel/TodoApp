package com.voxeldev.todoapp.di.dependencies

import android.content.Context
import com.voxeldev.todoapp.domain.usecase.todoitem.CreateTodoItemUseCase
import com.voxeldev.todoapp.domain.usecase.todoitem.DeleteTodoItemUseCase
import com.voxeldev.todoapp.domain.usecase.todoitem.GetSingleTodoItemUseCase
import com.voxeldev.todoapp.domain.usecase.todoitem.UpdateTodoItemUseCase
import com.voxeldev.todoapp.task.di.TaskFeatureDependencies
import com.voxeldev.todoapp.utils.platform.NetworkObserver
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import dagger.Module
import dagger.Provides

/**
 * @author nvoxel
 */
@Module
class TaskFeatureDependenciesModule {

    @Provides
    fun provideTaskFeatureDependencies(
        applicationContext: Context,
        createTodoItemUseCase: CreateTodoItemUseCase,
        deleteTodoItemUseCase: DeleteTodoItemUseCase,
        getSingleTodoItemUseCase: GetSingleTodoItemUseCase,
        updateTodoItemUseCase: UpdateTodoItemUseCase,
        networkObserver: NetworkObserver,
        coroutineDispatcherProvider: CoroutineDispatcherProvider,
    ): TaskFeatureDependencies = TaskFeatureDependenciesImpl(
        applicationContext = applicationContext,
        createTodoItemUseCase = createTodoItemUseCase,
        deleteTodoItemUseCase = deleteTodoItemUseCase,
        getSingleTodoItemUseCase = getSingleTodoItemUseCase,
        updateTodoItemUseCase = updateTodoItemUseCase,
        networkObserver = networkObserver,
        coroutineDispatcherProvider = coroutineDispatcherProvider,
    )
}

internal class TaskFeatureDependenciesImpl(
    override val applicationContext: Context,
    override val createTodoItemUseCase: CreateTodoItemUseCase,
    override val deleteTodoItemUseCase: DeleteTodoItemUseCase,
    override val getSingleTodoItemUseCase: GetSingleTodoItemUseCase,
    override val updateTodoItemUseCase: UpdateTodoItemUseCase,
    override val networkObserver: NetworkObserver,
    override val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : TaskFeatureDependencies
