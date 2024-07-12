package com.voxeldev.todoapp.api.model

/**
 * Authentication token type.
 * @author nvoxel
 */
enum class AuthTokenType(val headerPrefix: String) {
    Bearer(headerPrefix = "Bearer"),
    OAuth(headerPrefix = "OAuth"),
}
