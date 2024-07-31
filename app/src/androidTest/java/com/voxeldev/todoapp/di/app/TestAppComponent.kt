package com.voxeldev.todoapp.di.app

import android.content.Context
import com.voxeldev.todoapp.TestTodoApp
import com.voxeldev.todoapp.di.dependencies.AboutFeatureDependenciesModule
import com.voxeldev.todoapp.di.dependencies.AuthFeatureDependenciesModule
import com.voxeldev.todoapp.di.dependencies.ListFeatureDependenciesModule
import com.voxeldev.todoapp.di.dependencies.SettingsFeatureDependenciesModule
import com.voxeldev.todoapp.di.dependencies.TaskFeatureDependenciesModule
import com.voxeldev.todoapp.di.modules.TestLocalModule
import com.voxeldev.todoapp.di.modules.TestRepositoryModule
import com.voxeldev.todoapp.di.modules.TestUtilsModule
import com.voxeldev.todoapp.utils.di.scopes.AppScope
import dagger.BindsInstance
import dagger.Component

/**
 * @author nvoxel
 */
@Component(
    modules = [
        TestUtilsModule::class,
        TestLocalModule::class,
        TestRepositoryModule::class,
        AboutFeatureDependenciesModule::class,
        AuthFeatureDependenciesModule::class,
        ListFeatureDependenciesModule::class,
        SettingsFeatureDependenciesModule::class,
        TaskFeatureDependenciesModule::class,
    ],
)
@AppScope
interface TestAppComponent : AppComponent {

    fun inject(testTodoApp: TestTodoApp)

    @Component.Factory
    interface TestAppComponentFactory {
        fun create(@BindsInstance applicationContext: Context): TestAppComponent
    }
}
