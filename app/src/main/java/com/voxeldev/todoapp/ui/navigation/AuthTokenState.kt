package com.voxeldev.todoapp.ui.navigation

/**
 * @author nvoxel
 */
sealed interface AuthTokenState {
    data object Loading : AuthTokenState
    data object Found : AuthTokenState
    data object NotFound : AuthTokenState
}
