package com.voxeldev.todoapp.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.voxeldev.todoapp.designsystem.preview.annotations.ComponentDayNightPreviews
import com.voxeldev.todoapp.designsystem.preview.base.PreviewBase
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette

/**
 * @author nvoxel
 */
@Composable
fun FullscreenLoader(
    modifier: Modifier = Modifier,
    progress: (() -> Float)? = null,
) {
    val appPalette = LocalAppPalette.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = appPalette.backPrimary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Loader(progress = progress)
    }
}

@Composable
fun Loader(progress: (() -> Float)? = null) {
    val appPalette = LocalAppPalette.current

    progress?.let {
        CircularProgressIndicator(
            progress = progress,
            color = appPalette.colorBlue,
        )
    } ?: CircularProgressIndicator(color = appPalette.colorBlue)
}

@ComponentDayNightPreviews
@Composable
private fun Preview() {
    PreviewBase {
        FullscreenLoader(progress = { 0.7f })
    }
}
