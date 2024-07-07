package com.voxeldev.todoapp.di.navigation

import android.content.Context
import com.voxeldev.todoapp.local.di.LocalModule
import com.voxeldev.todoapp.utils.di.UtilsModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * @author nvoxel
 */
@Component(modules = [UtilsModule::class, LocalModule::class])
@Singleton
interface NavigationComponent {

    fun inject(navigationContainer: NavigationContainer)

    @Component.Factory
    interface NavigationComponentFactory {
        fun create(@BindsInstance applicationContext: Context): NavigationComponent
    }
}
