package com.voxeldev.todoapp.settings.work

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.voxeldev.todoapp.domain.usecase.base.BaseUseCase
import com.voxeldev.todoapp.domain.usecase.preferences.GetAutoRefreshIntervalUseCase
import com.voxeldev.todoapp.network.todoapi.TodoItemListRemoteDataSource
import com.voxeldev.todoapp.utils.exceptions.TokenNotFoundException
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

/**
 * WorkManager Worker that periodically refreshes task list.
 * @author nvoxel
 */
class AutoRefreshWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParameters: WorkerParameters,
    private val todoItemListRemoteDataSource: TodoItemListRemoteDataSource,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : CoroutineWorker(appContext, workerParameters) {

    override suspend fun doWork(): Result = withContext(coroutineDispatcherProvider.ioDispatcher) {
        todoItemListRemoteDataSource.getAll().fold(
            onSuccess = {
                Log.i("AutoRefreshWorker", "Finished successfully")
                Result.success()
            },
            onFailure = { exception ->
                Log.i("AutoRefreshWorker", "Finished with error: ${exception.message ?: exception.toString()}")
                if (exception is TokenNotFoundException) {
                    Result.failure()
                } else {
                    Result.retry()
                }
            },
        )
    }

    companion object {
        const val WORK_NAME = "com.voxeldev.todoapp.AutoRefreshWorker"
    }
}

fun Activity.setupAutoRefreshWork(
    getAutoRefreshIntervalUseCase: GetAutoRefreshIntervalUseCase,
    scope: CoroutineScope,
) {
    getAutoRefreshIntervalUseCase(
        params = BaseUseCase.NoParams,
        scope = scope,
    ) { result ->
        result.onSuccess { repeatInterval ->
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(networkType = NetworkType.CONNECTED)
                .build()

            val autoRefreshRequest = PeriodicWorkRequestBuilder<AutoRefreshWorker>(
                repeatInterval = repeatInterval,
                repeatIntervalTimeUnit = TimeUnit.SECONDS,
            ).setConstraints(constraints = constraints).build()

            val workManager = WorkManager.getInstance(applicationContext)

            workManager.enqueueUniquePeriodicWork(
                AutoRefreshWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.UPDATE,
                autoRefreshRequest,
            )
        }
    }
}
