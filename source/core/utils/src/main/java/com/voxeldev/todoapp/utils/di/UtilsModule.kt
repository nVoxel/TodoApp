package com.voxeldev.todoapp.utils.di

import android.content.Context
import com.voxeldev.todoapp.utils.di.scopes.AppScope
import com.voxeldev.todoapp.utils.platform.DefaultNetworkHandler
import com.voxeldev.todoapp.utils.platform.DefaultNetworkObserver
import com.voxeldev.todoapp.utils.platform.NetworkHandler
import com.voxeldev.todoapp.utils.platform.NetworkObserver
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProviderDefaultImpl
import com.voxeldev.todoapp.utils.providers.StringResourceProvider
import com.voxeldev.todoapp.utils.providers.StringResourceProviderContextImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

/**
 * Utils module that stores auxiliary classes.
 * @author nvoxel
 */
@Module(
    includes = [
        UtilsModule.Provide::class,
        InternalUtilsModule::class,
    ],
)
abstract class UtilsModule {

    @Binds
    abstract fun bindCoroutineDispatcherProvider(
        coroutineDispatcherProviderDefaultImpl: CoroutineDispatcherProviderDefaultImpl,
    ): CoroutineDispatcherProvider

    @Module
    class Provide {

        @Provides
        fun provideStringResourceProvider(context: Context): StringResourceProvider =
            StringResourceProviderContextImpl(context = context)
    }
}

@Module
internal interface InternalUtilsModule {

    @Binds
    @AppScope
    fun bindNetworkHandler(
        defaultNetworkHandler: DefaultNetworkHandler,
    ): NetworkHandler

    @Binds
    @AppScope
    fun bindNetworkObserver(
        defaultNetworkObserver: DefaultNetworkObserver,
    ): NetworkObserver
}
