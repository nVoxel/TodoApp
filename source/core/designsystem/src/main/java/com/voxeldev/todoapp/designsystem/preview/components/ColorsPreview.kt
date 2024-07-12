package com.voxeldev.todoapp.designsystem.preview.components

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * @author nvoxel
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun ColorsPreview(colors: List<Pair<Color, String>>) {
    FlowRow {
        colors.forEach { color ->
            PreviewColorCard(
                color = color.first,
                colorNameLabel = color.second,
            )
        }
    }
}
