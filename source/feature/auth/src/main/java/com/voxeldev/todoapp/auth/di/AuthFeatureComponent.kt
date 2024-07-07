package com.voxeldev.todoapp.auth.di

import dagger.Component
import javax.inject.Scope

/**
 * @author nvoxel
 */
@Component(dependencies = [AuthFeatureDependencies::class])
@AuthFeatureScope
interface AuthFeatureComponent {

    fun inject(authScreenContainer: AuthScreenContainer)

    @Component.Factory
    interface AuthFeatureComponentFactory {
        fun create(dependencies: AuthFeatureDependencies): AuthFeatureComponent
    }
}

@Scope
annotation class AuthFeatureScope
