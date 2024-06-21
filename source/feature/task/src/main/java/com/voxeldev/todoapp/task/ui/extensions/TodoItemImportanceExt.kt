package com.voxeldev.todoapp.task.ui.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.voxeldev.todoapp.api.model.TodoItemImportance
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette
import com.voxeldev.todoapp.task.R

/**
 * @author nvoxel
 */
@Composable
internal fun TodoItemImportance.getDisplayText(): String =
    when (this) {
        TodoItemImportance.Low -> stringResource(id = R.string.low_importance)
        TodoItemImportance.Normal -> stringResource(id = R.string.normal_importance)
        TodoItemImportance.Urgent -> stringResource(id = R.string.high_importance)
    }

@Composable
internal fun TodoItemImportance.getDisplayColor(forDropdown: Boolean): Color =
    LocalAppPalette.current.let { appPalette ->
        if (this == TodoItemImportance.Urgent) return@let appPalette.colorRed
        if (forDropdown) appPalette.labelPrimary else appPalette.labelTertiary
    }
