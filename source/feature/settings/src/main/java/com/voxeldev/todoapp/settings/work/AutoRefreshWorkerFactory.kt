package com.voxeldev.todoapp.settings.work

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.voxeldev.todoapp.api.repository.TodoItemRepository
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider

/**
 * @author nvoxel
 */
class AutoRefreshWorkerFactory(
    private val todoItemRepository: TodoItemRepository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters,
    ): ListenableWorker? =
        when (workerClassName) {
            AutoRefreshWorker::class.java.name -> AutoRefreshWorker(
                appContext = appContext,
                workerParameters = workerParameters,
                todoItemRepository = todoItemRepository,
                coroutineDispatcherProvider = coroutineDispatcherProvider,
            )

            else -> null
        }
}
