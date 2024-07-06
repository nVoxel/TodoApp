package com.voxeldev.todoapp.settings.di

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
interface SettingsFeatureComponent {

    fun inject(settingsScreenContainer: SettingsScreenContainer)

    @Component.Factory
    interface SettingsFeatureComponentFactory {
        fun create(@BindsInstance applicationContext: Context): SettingsFeatureComponent
    }
}
