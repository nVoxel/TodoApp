package com.voxeldev.todoapp.settings.di

import dagger.Component
import javax.inject.Scope

/**
 * @author nvoxel
 */
@Component(dependencies = [SettingsFeatureDependencies::class])
@SettingsFeatureScope
interface SettingsFeatureComponent {

    fun inject(settingsScreenContainer: SettingsScreenContainer)

    @Component.Factory
    interface SettingsFeatureComponentFactory {
        fun create(dependencies: SettingsFeatureDependencies): SettingsFeatureComponent
    }
}

@Scope
annotation class SettingsFeatureScope
