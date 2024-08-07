package com.voxeldev.todoapp.api.repository

import com.voxeldev.todoapp.api.model.AuthToken

/**
 * Stores authentication token.
 * @author nvoxel
 */
interface AuthTokenRepository {

    fun getToken(): Result<AuthToken>

    fun setToken(token: AuthToken): Result<Unit>

    fun clearToken(): Result<Unit>
}
