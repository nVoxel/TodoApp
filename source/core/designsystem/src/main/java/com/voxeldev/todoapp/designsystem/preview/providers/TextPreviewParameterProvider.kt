package com.voxeldev.todoapp.designsystem.preview.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

/**
 * Parameter provider that provides non-empty and empty strings
 * @author nvoxel
 */
internal class TextPreviewParameterProvider : PreviewParameterProvider<String> {
    override val values: Sequence<String> = sequenceOf("Text field that contains some text", "")
}
