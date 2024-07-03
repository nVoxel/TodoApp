package com.voxeldev.todoapp.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.designsystem.preview.annotations.ComponentDayNightPreviews
import com.voxeldev.todoapp.designsystem.preview.base.PreviewBase
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette

/**
 * @author nvoxel
 */
@Composable
fun TodoButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    content: @Composable (RowScope.() -> Unit),
) {
    val appPalette = LocalAppPalette.current

    TodoButton(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = appPalette.colorBlue,
            contentColor = appPalette.colorWhite,
        ),
        enabled = enabled,
        content = content,
    )
}

@Composable
fun TodoButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    shape: Shape = ButtonDefaults.shape,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = colors,
        content = content,
        shape = shape,
        enabled = enabled,
    )
}

@ComponentDayNightPreviews
@Composable
private fun Preview() {
    PreviewBase {
        Box(modifier = Modifier.padding(all = 8.dp)) {
            TodoButton(onClick = {}) {
                Text(text = "Button text")
            }
        }
    }
}
