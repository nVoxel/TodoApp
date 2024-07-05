package com.voxeldev.todoapp.auth.extensions

import android.graphics.Color.TRANSPARENT
import android.os.Build
import android.view.Window
import androidx.core.view.WindowInsetsControllerCompat

/**
 * @author nvoxel
 */
internal fun Window.setTransparentBars() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return
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
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return
    isStatusBarContrastEnforced = false
    isNavigationBarContrastEnforced = true
    WindowInsetsControllerCompat(this, decorView).run {
        isAppearanceLightStatusBars = !isSystemInDarkTheme
        isAppearanceLightNavigationBars = !isSystemInDarkTheme
    }
}
