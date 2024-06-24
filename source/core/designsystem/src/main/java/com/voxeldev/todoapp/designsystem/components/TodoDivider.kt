package com.voxeldev.todoapp.designsystem.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.designsystem.preview.base.PreviewBase
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette

/**
 * @author nvoxel
 */
@Composable
fun TodoDivider(modifier: Modifier = Modifier) {
    val appPalette = LocalAppPalette.current

    HorizontalDivider(
        modifier = modifier,
        thickness = 0.5.dp,
        color = appPalette.supportSeparator,
    )
}

@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    PreviewBase {
        Box(modifier = Modifier.padding(all = 8.dp)) {
            TodoDivider()
        }
    }
}
