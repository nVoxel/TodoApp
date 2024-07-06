package com.voxeldev.todoapp.local.di

import com.voxeldev.todoapp.api.repository.AuthTokenRepository
import com.voxeldev.todoapp.api.repository.PreferencesRepository
import com.voxeldev.todoapp.local.preferences.PreferencesRepositoryDefaultImpl
import com.voxeldev.todoapp.local.token.AuthTokenRepositoryPrefsImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Local data module.
 * @author nvoxel
 */
@Module(includes = [InternalLocalModule::class])
interface LocalModule

@Module
internal interface InternalLocalModule {

    @Binds
    @Singleton
    fun bindPreferencesRepository(
        preferencesRepositoryDefaultImpl: PreferencesRepositoryDefaultImpl,
    ): PreferencesRepository

    @Binds
    @Singleton
    fun bindAuthTokenRepository(
        authTokenRepositoryPrefsImpl: AuthTokenRepositoryPrefsImpl,
    ): AuthTokenRepository
}
