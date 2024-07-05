package com.voxeldev.todoapp.ui.navigation.state

/**
 * Represents current authentication state.
 * @author nvoxel
 */
internal sealed interface AuthTokenState {
    data object Loading : AuthTokenState
    data object Found : AuthTokenState
    data object NotFound : AuthTokenState
}
