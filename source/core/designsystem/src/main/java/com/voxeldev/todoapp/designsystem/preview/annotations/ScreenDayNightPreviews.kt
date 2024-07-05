package com.voxeldev.todoapp.designsystem.preview.annotations

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

/**
 * @author nvoxel
 */
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ru")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ru")
annotation class ScreenDayNightPreviews
