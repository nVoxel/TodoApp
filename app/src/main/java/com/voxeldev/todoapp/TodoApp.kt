package com.voxeldev.todoapp

import android.app.Application
import androidx.work.Configuration
import com.voxeldev.todoapp.about.compose.AboutFeatureDependencies
import com.voxeldev.todoapp.auth.di.AuthFeatureDependencies
import com.voxeldev.todoapp.di.app.AppComponent
import com.voxeldev.todoapp.di.app.DaggerAppComponent
import com.voxeldev.todoapp.list.di.ListFeatureDependencies
import com.voxeldev.todoapp.settings.di.SettingsFeatureDependencies
import com.voxeldev.todoapp.task.di.TaskFeatureDependencies
import com.voxeldev.todoapp.utils.extensions.lazyUnsafe
import com.voxeldev.todoapp.work.TodoAppWorkerFactory
import javax.inject.Inject

/**
 * @author nvoxel
 */
class TodoApp : Application(), Configuration.Provider {

    private val appComponent: AppComponent by lazyUnsafe {
        DaggerAppComponent.factory().create(applicationContext = applicationContext)
    }

    @Inject
    lateinit var aboutFeatureDependencies: AboutFeatureDependencies

    @Inject
    lateinit var authFeatureDependencies: AuthFeatureDependencies

    @Inject
    lateinit var listFeatureDependencies: ListFeatureDependencies

    @Inject
    lateinit var settingsFeatureDependencies: SettingsFeatureDependencies

    @Inject
    lateinit var taskFeatureDependencies: TaskFeatureDependencies

    @Inject
    lateinit var todoAppWorkerFactory: TodoAppWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(todoAppWorkerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(todoApp = this)
    }
}
