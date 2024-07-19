package com.voxeldev.todoapp.task.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.voxeldev.todoapp.domain.usecase.todoitem.CreateTodoItemUseCase
import com.voxeldev.todoapp.domain.usecase.todoitem.DeleteTodoItemUseCase
import com.voxeldev.todoapp.domain.usecase.todoitem.GetSingleTodoItemUseCase
import com.voxeldev.todoapp.domain.usecase.todoitem.UpdateTodoItemUseCase
import com.voxeldev.todoapp.utils.platform.NetworkObserver
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

/**
 * @author nvoxel
 */
class TaskViewModelProvider @AssistedInject constructor(
    @Assisted val taskId: String?,
    private val createTodoItemUseCase: CreateTodoItemUseCase,
    private val deleteTodoItemUseCase: DeleteTodoItemUseCase,
    private val getSingleTodoItemUseCase: GetSingleTodoItemUseCase,
    private val updateTodoItemUseCase: UpdateTodoItemUseCase,
    private val networkObserver: NetworkObserver,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : ViewModelProvider.Factory {

    @AssistedFactory
    interface Factory {
        fun create(taskId: String?): TaskViewModelProvider
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T = TaskViewModel(
        taskId = taskId,
        createTodoItemUseCase = createTodoItemUseCase,
        deleteTodoItemUseCase = deleteTodoItemUseCase,
        getSingleTodoItemUseCase = getSingleTodoItemUseCase,
        updateTodoItemUseCase = updateTodoItemUseCase,
        networkObserver = networkObserver,
        coroutineDispatcherProvider = coroutineDispatcherProvider,
    ) as T
}
