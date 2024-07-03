package com.voxeldev.todoapp.local.token

import kotlinx.serialization.Serializable

/**
 * @author nvoxel
 */
@Serializable
data class StoredToken(
    val token: String,
    val type: String,
)
