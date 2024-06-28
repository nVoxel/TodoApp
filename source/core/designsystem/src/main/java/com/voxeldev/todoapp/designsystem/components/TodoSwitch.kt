package com.voxeldev.todoapp.designsystem.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.designsystem.preview.base.PreviewBase
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

@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewUnchecked() {
    PreviewBase {
        Box(modifier = Modifier.padding(all = 8.dp)) {
            TodoSwitch(
                checked = false,
                onCheckedChange = {},
            )
        }
    }
}

@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewChecked() {
    PreviewBase {
        Box(modifier = Modifier.padding(all = 8.dp)) {
            TodoSwitch(
                checked = true,
                onCheckedChange = {},
            )
        }
    }
}
