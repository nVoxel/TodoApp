package com.voxeldev.todoapp.di

import android.content.Context
import com.voxeldev.todoapp.local.di.LocalModule
import com.voxeldev.todoapp.network.di.NetworkModule
import com.voxeldev.todoapp.network.di.RepositoryModule
import com.voxeldev.todoapp.ui.activity.MainActivity
import com.voxeldev.todoapp.utils.di.UtilsModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * @author nvoxel
 */
@Component(modules = [UtilsModule::class, LocalModule::class, NetworkModule::class, RepositoryModule::class])
@Singleton
interface MainActivityComponent {

    @Component.Factory
    interface MainActivityComponentFactory {
        fun create(@BindsInstance applicationContext: Context): MainActivityComponent
    }

    fun inject(activity: MainActivity)
}
