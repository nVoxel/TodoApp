package com.voxeldev.todoapp.di.stubs.local

import com.voxeldev.todoapp.api.model.AuthToken
import com.voxeldev.todoapp.api.model.AuthTokenType
import com.voxeldev.todoapp.api.repository.AuthTokenRepository
import javax.inject.Inject

/**
 * @author nvoxel
 */
class StubAuthTokenRepository @Inject constructor() : AuthTokenRepository {

    override fun getToken(): Result<AuthToken> = Result.success(
        AuthToken(
            token = "password",
            type = AuthTokenType.Bearer,
        ),
    )

    override fun setToken(token: AuthToken): Result<Unit> = Result.success(Unit)

    override fun clearToken(): Result<Unit> = Result.success(Unit)
}
