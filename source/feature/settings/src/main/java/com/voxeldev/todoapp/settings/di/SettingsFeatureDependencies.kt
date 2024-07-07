package com.voxeldev.todoapp.settings.di

import android.content.Context
import com.voxeldev.todoapp.api.repository.AuthTokenRepository
import com.voxeldev.todoapp.utils.platform.NetworkObserver
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider

/**
 * @author nvoxel
 */
interface SettingsFeatureDependencies {

    val applicationContext: Context

    val authTokenRepository: AuthTokenRepository
    val networkObserver: NetworkObserver
    val coroutineDispatcherProvider: CoroutineDispatcherProvider
}
