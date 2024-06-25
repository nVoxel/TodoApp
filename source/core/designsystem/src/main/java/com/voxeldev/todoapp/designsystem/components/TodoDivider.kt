package com.voxeldev.todoapp.designsystem.components

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
