package com.voxeldev.todoapp.local.preferences

import android.content.Context
import android.provider.Settings.Secure
import com.voxeldev.todoapp.api.model.AppTheme
import com.voxeldev.todoapp.api.repository.PreferencesRepository
import com.voxeldev.todoapp.local.mapper.AppThemeMapper
import javax.inject.Inject

/**
 * Default implementation of [PreferencesRepository] that uses unencrypted shared preferences.
 * @author nvoxel
 */
internal class PreferencesRepositoryDefaultImpl @Inject constructor(
    private val context: Context,
    private val appThemeMapper: AppThemeMapper,
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

    override fun getAppTheme(): Result<AppTheme> = runCatching {
        appThemeMapper.fromPreference(
            preference = sharedPreferences.getString(APP_THEME_PREF_NAME, DEFAULT_APP_THEME)!!,
        )
    }

    override fun setAppTheme(appTheme: AppTheme): Result<Unit> = runCatching {
        sharedPreferencesEditor.putString(
            APP_THEME_PREF_NAME,
            appThemeMapper.toPreference(appTheme = appTheme),
        )
        sharedPreferencesEditor.apply()
    }

    private companion object {
        val DEFAULT_APP_THEME = AppTheme.Auto.name

        const val SHARED_PREFS_FILE_NAME = "settings"
        const val DEVICE_ID_PREF_NAME = "device_id"
        const val AUTO_REFRESH_INTERVAL_PREF_NAME = "auto_refresh_interval"
        const val APP_THEME_PREF_NAME = "app_theme"

        const val DEFAULT_AUTO_REFRESH_INTERVAL = 28_800L
    }
}
