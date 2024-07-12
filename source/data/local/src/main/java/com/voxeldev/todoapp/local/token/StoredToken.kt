package com.voxeldev.todoapp.local.token

import kotlinx.serialization.Serializable

/**
 * Represents authentication token stored in shared preferences.
 * @author nvoxel
 */
@Serializable
data class StoredToken(
    val token: String,
    val type: String,
)
