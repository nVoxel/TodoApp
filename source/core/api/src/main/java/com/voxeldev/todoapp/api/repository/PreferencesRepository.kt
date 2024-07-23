package com.voxeldev.todoapp.api.repository

import com.voxeldev.todoapp.api.model.AppTheme

/**
 * Stores various user preferences.
 * @author nvoxel
 */
interface PreferencesRepository {

    /**
     * @return Current device's ID
     */
    fun getDeviceID(): Result<String>

    /**
     * @return Current auto refresh interval in seconds
     */
    fun getAutoRefreshInterval(): Result<Long>

    /**
     * @param autoRefreshInterval Current refresh interval in seconds
     */
    fun setAutoRefreshInterval(autoRefreshInterval: Long): Result<Unit>

    /**
     * @return Application theme mode
     */
    fun getAppTheme(): Result<AppTheme>

    /**
     * @param appTheme Current application theme mode
     */
    fun setAppTheme(appTheme: AppTheme): Result<Unit>
}
