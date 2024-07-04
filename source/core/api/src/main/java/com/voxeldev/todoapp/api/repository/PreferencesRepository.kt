package com.voxeldev.todoapp.api.repository

/**
 * @author nvoxel
 */
interface PreferencesRepository {

    fun getDeviceID(): Result<String>

    fun getAutoRefreshInterval(): Result<Long>
    fun setAutoRefreshInterval(autoRefreshInterval: Long): Result<Unit>
}
