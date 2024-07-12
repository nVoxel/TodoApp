package com.voxeldev.todoapp.api.model

/**
 * Authentication token that is used for the backend requests.
 * @author nvoxel
 */
data class AuthToken(
    val token: String,
    val type: AuthTokenType,
)
