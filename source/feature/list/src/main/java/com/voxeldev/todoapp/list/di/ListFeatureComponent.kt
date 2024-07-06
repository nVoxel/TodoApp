package com.voxeldev.todoapp.list.di

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
interface ListFeatureComponent {

    fun inject(listScreenContainer: ListScreenContainer)

    @Component.Factory
    interface ListFeatureComponentFactory {
        fun create(@BindsInstance applicationContext: Context): ListFeatureComponent
    }
}
