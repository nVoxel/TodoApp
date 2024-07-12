package com.voxeldev.todoapp.designsystem.preview.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

/**
 * Parameter provider with two values
 * @author nvoxel
 */
internal class BooleanPreviewParameterProvider : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean> = sequenceOf(false, true)
}
