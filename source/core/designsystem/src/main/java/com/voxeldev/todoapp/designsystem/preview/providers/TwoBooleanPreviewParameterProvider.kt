package com.voxeldev.todoapp.designsystem.preview.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

/**
 * @author nvoxel
 */
internal class TwoBooleanPreviewParameterProvider : PreviewParameterProvider<Pair<Boolean, Boolean>> {
    override val values: Sequence<Pair<Boolean, Boolean>> = sequenceOf(
        false to false,
        false to true,
        true to false,
        true to true,
    )
}
