package com.voxeldev.todoapp.auth.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.voxeldev.todoapp.designsystem.components.TodoButton
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette

/**
 * @author nvoxel
 */
@Composable
internal fun AuthContrastButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    val appPalette = LocalAppPalette.current

    TodoButton(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 58.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = appPalette.backContrast,
        ),
        shape = RoundedCornerShape(size = 24.dp),
        enabled = enabled,
        content = content,
    )
}
