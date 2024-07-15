package com.voxeldev.todoapp.settings.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.api.model.AppTheme
import com.voxeldev.todoapp.designsystem.components.TodoInfoDialog
import com.voxeldev.todoapp.designsystem.components.TodoRadioButton
import com.voxeldev.todoapp.designsystem.preview.annotations.ScreenDayNightPreviews
import com.voxeldev.todoapp.designsystem.preview.base.PreviewBase
import com.voxeldev.todoapp.designsystem.theme.AppTypography
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette
import com.voxeldev.todoapp.settings.R
import com.voxeldev.todoapp.settings.extensions.getDisplayMessage

/**
 * @author nvoxel
 */
@Composable
internal fun SettingsThemeDialog(
    selectedTheme: AppTheme,
    onThemeSelected: (AppTheme) -> Unit,
    isVisible: Boolean,
    onDismiss: () -> Unit,
) {
    val appPalette = LocalAppPalette.current

    TodoInfoDialog(
        isVisible = isVisible,
        onDismiss = onDismiss,
        titleContent = {
            Text(
                text = stringResource(id = R.string.app_theme),
                color = appPalette.labelPrimary,
                style = AppTypography.title,
            )
        },
    ) {
        AppTheme.entries.forEach { appTheme ->
            SettingsThemeDialogItem(
                value = appTheme,
                isSelected = selectedTheme == appTheme,
                onClick = { onThemeSelected(appTheme) },
            )
        }
    }
}

@Composable
private fun SettingsThemeDialogItem(
    value: AppTheme,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val appPalette = LocalAppPalette.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TodoRadioButton(
            isSelected = isSelected,
            onClick = onClick,
        )

        Spacer(modifier = Modifier.width(width = 16.dp))

        Text(
            text = value.getDisplayMessage(),
            color = appPalette.labelPrimary,
            style = AppTypography.body,
        )
    }
}

@ScreenDayNightPreviews
@Composable
private fun Preview() {
    PreviewBase {
        SettingsThemeDialog(
            selectedTheme = AppTheme.Auto,
            onThemeSelected = {},
            isVisible = true,
            onDismiss = {},
        )
    }
}
