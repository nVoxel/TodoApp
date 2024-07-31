package com.voxeldev.todoapp.di.stubs.local

import com.voxeldev.todoapp.api.model.AppTheme
import com.voxeldev.todoapp.api.repository.PreferencesRepository
import javax.inject.Inject

/**
 * @author nvoxel
 */
class StubPreferencesRepository @Inject constructor() : PreferencesRepository {

    override fun getDeviceID(): Result<String> = Result.success(STUB_DEVICE_ID)

    override fun getAutoRefreshInterval(): Result<Long> = Result.success(STUB_AUTO_REFRESH_INTERVAL)

    override fun setAutoRefreshInterval(autoRefreshInterval: Long): Result<Unit> = Result.success(Unit)

    override fun getAppTheme(): Result<AppTheme> = Result.success(AppTheme.Auto)

    override fun setAppTheme(appTheme: AppTheme): Result<Unit> = Result.success(Unit)

    private companion object {
        const val STUB_DEVICE_ID = "device-id"
        const val STUB_AUTO_REFRESH_INTERVAL = 28_800L
    }
}
