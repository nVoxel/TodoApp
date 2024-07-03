package com.voxeldev.todoapp.api.model

/**
 * @author nvoxel
 */
data class AuthToken(
    val token: String,
    val type: AuthTokenType,
)
