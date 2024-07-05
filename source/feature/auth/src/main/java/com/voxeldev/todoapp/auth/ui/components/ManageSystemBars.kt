package com.voxeldev.todoapp.auth.ui.components

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.voxeldev.todoapp.auth.extensions.setTranslucentBars
import com.voxeldev.todoapp.auth.extensions.setTransparentBars

/**
 * @author nvoxel
 */
@Composable
internal fun ManageSystemBars() {
    val window = (LocalContext.current as Activity).window
    val isSystemInDarkTheme = isSystemInDarkTheme()

    LaunchedEffect(key1 = Unit) {
        window.setTransparentBars()
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            window.setTranslucentBars(
                isSystemInDarkTheme = isSystemInDarkTheme,
            )
        }
    }
}
