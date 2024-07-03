package com.voxeldev.todoapp.auth.extensions

import android.graphics.Color.TRANSPARENT
import android.view.Window
import androidx.core.view.WindowInsetsControllerCompat

/**
 * @author nvoxel
 */
internal fun Window.setTransparentBars() {
    statusBarColor = TRANSPARENT
    navigationBarColor = TRANSPARENT
    isStatusBarContrastEnforced = false
    isNavigationBarContrastEnforced = false
    WindowInsetsControllerCompat(this, decorView).run {
        isAppearanceLightStatusBars = false
        isAppearanceLightNavigationBars = false
    }
}

internal fun Window.setTranslucentBars(isSystemInDarkTheme: Boolean) {
    isStatusBarContrastEnforced = false
    isNavigationBarContrastEnforced = true
    WindowInsetsControllerCompat(this, decorView).run {
        isAppearanceLightStatusBars = !isSystemInDarkTheme
        isAppearanceLightNavigationBars = !isSystemInDarkTheme
    }
}
