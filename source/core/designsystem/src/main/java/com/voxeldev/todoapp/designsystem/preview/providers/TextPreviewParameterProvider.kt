package com.voxeldev.todoapp.designsystem.preview.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

/**
 * @author nvoxel
 */
internal class TextPreviewParameterProvider : PreviewParameterProvider<String> {
    override val values: Sequence<String> = sequenceOf("Text field that contains some text", "")
}
