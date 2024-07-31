package com.voxeldev.todoapp.di.modules

import com.voxeldev.todoapp.api.repository.AuthTokenRepository
import com.voxeldev.todoapp.api.repository.PreferencesRepository
import com.voxeldev.todoapp.di.stubs.local.StubAuthTokenRepository
import com.voxeldev.todoapp.di.stubs.local.StubPreferencesRepository
import dagger.Binds
import dagger.Module

/**
 * @author nvoxel
 */
@Module
interface TestLocalModule {

    @Binds
    fun bindPreferencesRepository(
        stubPreferencesRepository: StubPreferencesRepository,
    ): PreferencesRepository

    @Binds
    fun bindAuthTokenRepository(
        stubAuthTokenRepository: StubAuthTokenRepository,
    ): AuthTokenRepository
}
