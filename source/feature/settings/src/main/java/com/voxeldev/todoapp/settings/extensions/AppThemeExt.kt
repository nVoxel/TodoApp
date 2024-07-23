package com.voxeldev.todoapp.settings.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.voxeldev.todoapp.api.model.AppTheme
import com.voxeldev.todoapp.settings.R

/**
 * @author nvoxel
 */
@Composable
internal fun AppTheme.getDisplayMessage(): String = stringResource(
    id = when (this) {
        AppTheme.Auto -> R.string.app_theme_auto
        AppTheme.Light -> R.string.app_theme_light
        AppTheme.Dark -> R.string.app_theme_dark
    },
)
