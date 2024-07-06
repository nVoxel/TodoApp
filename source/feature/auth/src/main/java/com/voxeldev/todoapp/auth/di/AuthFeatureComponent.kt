package com.voxeldev.todoapp.auth.di

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
interface AuthFeatureComponent {

    fun inject(authScreenContainer: AuthScreenContainer)

    @Component.Factory
    interface AuthFeatureComponentFactory {
        fun create(@BindsInstance applicationContext: Context): AuthFeatureComponent
    }
}
