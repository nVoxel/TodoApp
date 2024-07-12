package com.voxeldev.todoapp.local.token

import com.voxeldev.todoapp.api.model.AuthToken
import com.voxeldev.todoapp.api.model.AuthTokenType
import com.voxeldev.todoapp.utils.exceptions.CorruptedTokenException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Maps [AuthToken] to [StoredToken] and vice versa.
 * @author nvoxel
 */
@Singleton
internal class StoredTokenMapper @Inject constructor() {

    fun toStored(authToken: AuthToken): StoredToken =
        StoredToken(
            token = authToken.token,
            type = authToken.type.toStored(),
        )

    fun toModel(storedToken: StoredToken): AuthToken =
        AuthToken(
            token = storedToken.token,
            type = storedToken.type.toTokenType(),
        )

    private fun AuthTokenType.toStored(): String =
        when (this) {
            AuthTokenType.Bearer -> TYPE_BEARER
            AuthTokenType.OAuth -> TYPE_OAUTH
        }

    private fun String.toTokenType(): AuthTokenType =
        when (this) {
            TYPE_BEARER -> AuthTokenType.Bearer
            TYPE_OAUTH -> AuthTokenType.OAuth
            else -> throw CorruptedTokenException()
        }

    private companion object {
        const val TYPE_BEARER = "bearer"
        const val TYPE_OAUTH = "oauth"
    }
}
