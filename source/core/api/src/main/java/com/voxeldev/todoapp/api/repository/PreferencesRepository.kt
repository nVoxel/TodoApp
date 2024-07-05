package com.voxeldev.todoapp.api.repository

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
}
