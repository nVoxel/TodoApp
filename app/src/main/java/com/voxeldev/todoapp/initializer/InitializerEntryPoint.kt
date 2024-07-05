package com.voxeldev.todoapp.initializer

import android.content.Context
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

/**
 * @author nvoxel
 */
@EntryPoint
@InstallIn(SingletonComponent::class)
internal interface InitializerEntryPoint {
    fun inject(workManagerInitializer: WorkManagerInitializer)

    companion object {
        fun resolve(context: Context): InitializerEntryPoint {
            val appContext = checkNotNull(context.applicationContext)
            return EntryPointAccessors.fromApplication(
                appContext,
                InitializerEntryPoint::class.java,
            )
        }
    }
}
