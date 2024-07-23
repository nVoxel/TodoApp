package com.voxeldev.todoapp.local.mapper

import com.voxeldev.todoapp.api.model.AppTheme
import javax.inject.Inject

/**
 * @author nvoxel
 */
class AppThemeMapper @Inject constructor() {

    fun toPreference(appTheme: AppTheme): String = appTheme.name

    fun fromPreference(preference: String): AppTheme = enumValueOf(preference)
}
