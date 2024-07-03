package com.voxeldev.todoapp.api.model

/**
 * @author nvoxel
 */
enum class AuthTokenType(val headerPrefix: String) {
    Bearer(headerPrefix = "Bearer"),
    OAuth(headerPrefix = "OAuth"),
}
