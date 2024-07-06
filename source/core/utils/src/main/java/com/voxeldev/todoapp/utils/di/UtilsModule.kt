package com.voxeldev.todoapp.utils.di

import android.content.Context
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProviderDefaultImpl
import com.voxeldev.todoapp.utils.providers.StringResourceProvider
import com.voxeldev.todoapp.utils.providers.StringResourceProviderContextImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Utils module that stores auxiliary classes.
 * @author nvoxel
 */
@Module(includes = [UtilsModule.Provide::class])
abstract class UtilsModule {

    @Binds
    @Singleton
    abstract fun bindCoroutineDispatcherProvider(
        coroutineDispatcherProviderDefaultImpl: CoroutineDispatcherProviderDefaultImpl,
    ): CoroutineDispatcherProvider

    @Module
    class Provide {

        @Provides
        @Singleton
        fun provideStringResourceProvider(context: Context): StringResourceProvider =
            StringResourceProviderContextImpl(context = context)
    }
}
