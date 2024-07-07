package com.voxeldev.todoapp.di.dependencies

import android.content.Context
import com.voxeldev.todoapp.domain.usecase.token.ClearAuthTokenUseCase
import com.voxeldev.todoapp.settings.di.SettingsFeatureDependencies
import com.voxeldev.todoapp.utils.platform.NetworkObserver
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import dagger.Module
import dagger.Provides

/**
 * @author nvoxel
 */
@Module
class SettingsFeatureDependenciesModule {

    @Provides
    fun provideSettingsFeatureDependencies(
        applicationContext: Context,
        clearAuthTokenUseCase: ClearAuthTokenUseCase,
        networkObserver: NetworkObserver,
        coroutineDispatcherProvider: CoroutineDispatcherProvider,
    ): SettingsFeatureDependencies = SettingsFeatureDependenciesImpl(
        applicationContext = applicationContext,
        clearAuthTokenUseCase = clearAuthTokenUseCase,
        networkObserver = networkObserver,
        coroutineDispatcherProvider = coroutineDispatcherProvider,
    )
}

internal class SettingsFeatureDependenciesImpl(
    override val applicationContext: Context,
    override val clearAuthTokenUseCase: ClearAuthTokenUseCase,
    override val networkObserver: NetworkObserver,
    override val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : SettingsFeatureDependencies
