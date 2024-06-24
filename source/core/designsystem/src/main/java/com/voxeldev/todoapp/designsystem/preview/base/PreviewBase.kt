package com.voxeldev.todoapp.designsystem.preview.base

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette
import com.voxeldev.todoapp.designsystem.theme.TodoAppTheme

/**
 * @author nvoxel
 */
@Composable
fun PreviewBase(previewContent: @Composable () -> Unit) {
    TodoAppTheme {
        val appPalette = LocalAppPalette.current

        Surface(
            modifier = Modifier.wrapContentSize(),
            color = appPalette.backPrimary,
            content = previewContent,
        )
    }
}
