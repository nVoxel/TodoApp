package com.voxeldev.todoapp.local.preferences

import android.content.Context
import android.provider.Settings.Secure
import com.voxeldev.todoapp.api.repository.PreferencesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Default implementation of [PreferencesRepository] that uses unencrypted shared preferences.
 * @author nvoxel
 */
internal class PreferencesRepositoryDefaultImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : PreferencesRepository {

    private val sharedPreferences = context.getSharedPreferences(
        SHARED_PREFS_FILE_NAME,
        Context.MODE_PRIVATE,
    )

    private val sharedPreferencesEditor = sharedPreferences.edit()

    override fun getDeviceID(): Result<String> = runCatching {
        sharedPreferences.getString(DEVICE_ID_PREF_NAME, null)
            ?: obtainDeviceID().also(::setDeviceID)
    }

    private fun setDeviceID(deviceId: String): Result<Unit> = runCatching {
        sharedPreferencesEditor.putString(DEVICE_ID_PREF_NAME, deviceId)
        sharedPreferencesEditor.apply()
    }

    private fun obtainDeviceID(): String =
        Secure.getString(context.contentResolver, Secure.ANDROID_ID)

    override fun getAutoRefreshInterval(): Result<Long> = runCatching {
        sharedPreferences.getLong(AUTO_REFRESH_INTERVAL_PREF_NAME, DEFAULT_AUTO_REFRESH_INTERVAL)
    }

    override fun setAutoRefreshInterval(autoRefreshInterval: Long): Result<Unit> = runCatching {
        sharedPreferencesEditor.putLong(AUTO_REFRESH_INTERVAL_PREF_NAME, autoRefreshInterval)
        sharedPreferencesEditor.apply()
    }

    private companion object {
        const val SHARED_PREFS_FILE_NAME = "settings"
        const val DEVICE_ID_PREF_NAME = "device_id"
        const val AUTO_REFRESH_INTERVAL_PREF_NAME = "auto_refresh_interval"

        const val DEFAULT_AUTO_REFRESH_INTERVAL = 28_800L
    }
}
