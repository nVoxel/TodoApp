package com.voxeldev.todoapp.list.di

import dagger.Component
import javax.inject.Scope

/**
 * @author nvoxel
 */
@Component(dependencies = [ListFeatureDependencies::class])
@ListFeatureScope
interface ListFeatureComponent {

    fun inject(listScreenContainer: ListScreenContainer)

    @Component.Factory
    interface ListFeatureComponentFactory {
        fun create(dependencies: ListFeatureDependencies): ListFeatureComponent
    }
}

@Scope
annotation class ListFeatureScope
