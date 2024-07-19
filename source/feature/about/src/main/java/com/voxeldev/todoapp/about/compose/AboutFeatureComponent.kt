package com.voxeldev.todoapp.about.compose

import com.voxeldev.todoapp.about.di.AboutScreenContainer
import dagger.Component
import javax.inject.Scope

/**
 * @author nvoxel
 */
@Component(dependencies = [AboutFeatureDependencies::class])
@AboutFeatureScope
interface AboutFeatureComponent {

    fun inject(aboutScreenContainer: AboutScreenContainer)

    @Component.Factory
    interface AboutFeatureComponentFactory {
        fun create(dependencies: AboutFeatureDependencies): AboutFeatureComponent
    }
}

@Scope
annotation class AboutFeatureScope
