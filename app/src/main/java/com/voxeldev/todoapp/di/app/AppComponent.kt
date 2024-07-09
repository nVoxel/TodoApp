package com.voxeldev.todoapp.di.app

import android.content.Context
import com.voxeldev.todoapp.TodoApp
import com.voxeldev.todoapp.di.dependencies.AuthFeatureDependenciesModule
import com.voxeldev.todoapp.di.dependencies.ListFeatureDependenciesModule
import com.voxeldev.todoapp.di.dependencies.SettingsFeatureDependenciesModule
import com.voxeldev.todoapp.di.dependencies.TaskFeatureDependenciesModule
import com.voxeldev.todoapp.local.di.LocalModule
import com.voxeldev.todoapp.repository.di.RepositoryModule
import com.voxeldev.todoapp.utils.di.UtilsModule
import com.voxeldev.todoapp.utils.di.scopes.AppScope
import dagger.BindsInstance
import dagger.Component

/**
 * @author nvoxel
 */
@Component(
    modules = [
        UtilsModule::class,
        LocalModule::class,
        RepositoryModule::class,
        AuthFeatureDependenciesModule::class,
        ListFeatureDependenciesModule::class,
        SettingsFeatureDependenciesModule::class,
        TaskFeatureDependenciesModule::class,
    ],
)
@AppScope
interface AppComponent {

    fun inject(todoApp: TodoApp)

    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}
