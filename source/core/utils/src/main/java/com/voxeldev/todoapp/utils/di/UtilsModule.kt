package com.voxeldev.todoapp.utils.di

import android.content.Context
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProviderDefaultImpl
import com.voxeldev.todoapp.utils.providers.StringResourceProvider
import com.voxeldev.todoapp.utils.providers.StringResourceProviderContextImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author nvoxel
 */
@Module(includes = [UtilsModule.Provide::class])
@InstallIn(SingletonComponent::class)
abstract class UtilsModule {

    @Binds
    @Singleton
    abstract fun bindCoroutineDispatcherProvider(
        coroutineDispatcherProviderDefaultImpl: CoroutineDispatcherProviderDefaultImpl,
    ): CoroutineDispatcherProvider

    @Module
    @InstallIn(SingletonComponent::class)
    class Provide {

        @Provides
        @Singleton
        fun provideStringResourceProvider(
            @ApplicationContext context: Context,
        ): StringResourceProvider = StringResourceProviderContextImpl(context = context)
    }
}
