package com.voxeldev.todoapp.designsystem.preview.annotations

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

/**
 * @author nvoxel
 */
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
internal annotation class ComponentDayNightPreviews
