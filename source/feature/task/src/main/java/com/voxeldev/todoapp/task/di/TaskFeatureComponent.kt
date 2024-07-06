package com.voxeldev.todoapp.task.di

import android.content.Context
import com.voxeldev.todoapp.local.di.LocalModule
import com.voxeldev.todoapp.network.di.RepositoryModule
import com.voxeldev.todoapp.utils.di.UtilsModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * @author nvoxel
 */
@Component(modules = [UtilsModule::class, LocalModule::class, RepositoryModule::class])
@Singleton
interface TaskFeatureComponent {

    fun inject(taskScreenContainer: TaskScreenContainer)

    @Component.Factory
    interface TaskFeatureComponentFactory {
        fun create(@BindsInstance applicationContext: Context): TaskFeatureComponent
    }
}
