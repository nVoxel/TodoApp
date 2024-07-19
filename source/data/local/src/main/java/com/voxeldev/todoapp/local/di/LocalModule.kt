package com.voxeldev.todoapp.local.di

import com.voxeldev.todoapp.api.repository.AuthTokenRepository
import com.voxeldev.todoapp.api.repository.PreferencesRepository
import com.voxeldev.todoapp.local.preferences.PreferencesRepositoryDefaultImpl
import com.voxeldev.todoapp.local.token.AuthTokenRepositoryPrefsImpl
import dagger.Binds
import dagger.Module

/**
 * Local data module.
 * @author nvoxel
 */
@Module(includes = [InternalLocalModule::class])
interface LocalModule

@Module
internal interface InternalLocalModule {

    @Binds
    fun bindPreferencesRepository(
        preferencesRepositoryDefaultImpl: PreferencesRepositoryDefaultImpl,
    ): PreferencesRepository

    @Binds
    fun bindAuthTokenRepository(
        authTokenRepositoryPrefsImpl: AuthTokenRepositoryPrefsImpl,
    ): AuthTokenRepository
}
