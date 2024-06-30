package com.voxeldev.todoapp.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.designsystem.preview.annotations.ComponentDayNightPreviews
import com.voxeldev.todoapp.designsystem.preview.base.PreviewBase
import com.voxeldev.todoapp.designsystem.preview.providers.BooleanPreviewParameterProvider
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette

/**
 * @author nvoxel
 */
@Composable
fun TodoSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    val appPalette = LocalAppPalette.current

    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
            uncheckedThumbColor = appPalette.backElevated,
            uncheckedTrackColor = appPalette.supportOverlay,
            uncheckedBorderColor = Color.Transparent,
            checkedThumbColor = appPalette.colorBlue,
            checkedTrackColor = appPalette.colorBlueBack,
        ),
    )
}

@ComponentDayNightPreviews
@Composable
private fun Preview(
    @PreviewParameter(BooleanPreviewParameterProvider::class)
    checked: Boolean,
) {
    PreviewBase {
        Box(modifier = Modifier.padding(all = 8.dp)) {
            TodoSwitch(
                checked = checked,
                onCheckedChange = {},
            )
        }
    }
}
