package com.voxeldev.todoapp.designsystem.components

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette

/**
 * @author nvoxel
 */
@Composable
fun TodoSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean,
) {
    val appPalette = LocalAppPalette.current

    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        enabled = enabled,
        colors = SwitchDefaults.colors(
            disabledUncheckedThumbColor = appPalette.backElevated,
            disabledUncheckedTrackColor = appPalette.supportOverlay,
            checkedThumbColor = appPalette.colorBlue,
            checkedTrackColor = appPalette.colorBlueBack,
        ),
    )
}
