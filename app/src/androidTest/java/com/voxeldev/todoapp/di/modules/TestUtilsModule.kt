package com.voxeldev.todoapp.di.modules

import android.content.Context
import com.voxeldev.todoapp.di.stubs.utils.StubNetworkHandler
import com.voxeldev.todoapp.di.stubs.utils.StubNetworkObserver
import com.voxeldev.todoapp.utils.di.UtilsModule
import com.voxeldev.todoapp.utils.di.scopes.AppScope
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
 * @author nvoxel
 */
@Module(includes = [UtilsModule.Provide::class])
abstract class TestUtilsModule {

    @Binds
    abstract fun bindCoroutineDispatcherProvider(
        coroutineDispatcherProviderDefaultImpl: CoroutineDispatcherProviderDefaultImpl,
    ): CoroutineDispatcherProvider

    @Binds
    @AppScope
    abstract fun bindNetworkHandler(
        stubNetworkHandler: StubNetworkHandler,
    ): NetworkHandler

    @Binds
    @AppScope
    abstract fun bindNetworkObserver(
        stubNetworkObserver: StubNetworkObserver,
    ): NetworkObserver

    @Module
    class Provide {

        @Provides
        fun provideStringResourceProvider(context: Context): StringResourceProvider =
            StringResourceProviderContextImpl(context = context)
    }
}
