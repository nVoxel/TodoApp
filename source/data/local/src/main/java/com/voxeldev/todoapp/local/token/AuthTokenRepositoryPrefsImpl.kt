package com.voxeldev.todoapp.local.token

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.voxeldev.todoapp.api.model.AuthToken
import com.voxeldev.todoapp.api.repository.AuthTokenRepository
import com.voxeldev.todoapp.utils.exceptions.TokenNotFoundException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

/**
 * Default implementation of [AuthTokenRepository] that uses encrypted shared preferences.
 * @author nvoxel
 */
internal class AuthTokenRepositoryPrefsImpl @Inject constructor(
    context: Context,
    private val storedTokenMapper: StoredTokenMapper,
) : AuthTokenRepository {

    private val masterKey: MasterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        SHARED_PREFS_FILE_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
    )

    private val sharedPreferencesEditor = sharedPreferences.edit()

    override fun getToken(): Result<AuthToken> {
        val storedTokenString = sharedPreferences.getString(TOKEN_PREF_NAME, null)
        val storedToken = storedTokenString?.let<String, StoredToken>(Json::decodeFromString)

        return storedToken?.let {
            runCatching {
                storedTokenMapper.toModel(storedToken = storedToken)
            }
        } ?: Result.failure(TokenNotFoundException())
    }

    override fun setToken(token: AuthToken): Result<Unit> {
        val storedToken = storedTokenMapper.toStored(authToken = token)
        val storedTokenString = storedToken.run(Json::encodeToString)

        return runCatching {
            sharedPreferencesEditor.putString(TOKEN_PREF_NAME, storedTokenString)
            sharedPreferencesEditor.apply()
        }
    }

    override fun clearToken(): Result<Unit> =
        runCatching {
            sharedPreferencesEditor.remove(TOKEN_PREF_NAME)
            sharedPreferencesEditor.apply()
        }

    private companion object {
        const val SHARED_PREFS_FILE_NAME = "tokens"
        const val TOKEN_PREF_NAME = "token"
    }
}
