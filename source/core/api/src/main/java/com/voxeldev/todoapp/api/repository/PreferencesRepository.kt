package com.voxeldev.todoapp.api.repository

/**
 * @author nvoxel
 */
interface PreferencesRepository {

    fun getDeviceID(): String

    fun getAutoRefreshInterval(): Long
}
