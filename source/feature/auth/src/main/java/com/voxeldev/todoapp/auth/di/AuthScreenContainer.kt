package com.voxeldev.todoapp.auth.di

import androidx.compose.runtime.Stable
import com.voxeldev.todoapp.auth.viewmodel.AuthViewModelProvider
import javax.inject.Inject

/**
 * @author nvoxel
 */
@Stable
class AuthScreenContainer {
    @Inject lateinit var authViewModelProvider: AuthViewModelProvider.Factory
}
