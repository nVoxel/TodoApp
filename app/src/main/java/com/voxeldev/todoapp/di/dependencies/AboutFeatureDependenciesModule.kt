package com.voxeldev.todoapp.di.dependencies

import com.voxeldev.todoapp.about.compose.AboutFeatureDependencies
import com.voxeldev.todoapp.utils.providers.StringResourceProvider
import dagger.Module
import dagger.Provides

/**
 * @author nvoxel
 */
@Module
internal class AboutFeatureDependenciesModule {

    @Provides
    fun provideAboutFeatureDependencies(
        stringResourceProvider: StringResourceProvider,
    ): AboutFeatureDependencies = AboutFeatureDependenciesImpl(
        stringResourceProvider = stringResourceProvider,
    )
}

internal class AboutFeatureDependenciesImpl(
    override val stringResourceProvider: StringResourceProvider,
) : AboutFeatureDependencies
