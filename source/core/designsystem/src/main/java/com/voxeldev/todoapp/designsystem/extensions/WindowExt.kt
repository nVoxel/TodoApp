package com.voxeldev.todoapp.designsystem.extensions

import android.graphics.Color.TRANSPARENT
import android.os.Build
import android.view.Window
import androidx.core.view.WindowInsetsControllerCompat
import com.voxeldev.todoapp.api.model.AppTheme

/**
 * @author nvoxel
 */
fun Window.setTransparentBars() {
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

fun Window.setTranslucentBars(appTheme: AppTheme, systemInDarkTheme: Boolean) {
    setTranslucentBars(
        isSystemInDarkTheme = when (appTheme) {
            AppTheme.Auto -> systemInDarkTheme
            AppTheme.Light -> false
            AppTheme.Dark -> true
        },
    )
}

fun Window.setTranslucentBars(isSystemInDarkTheme: Boolean) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return
    isStatusBarContrastEnforced = false
    isNavigationBarContrastEnforced = true
    WindowInsetsControllerCompat(this, decorView).run {
        isAppearanceLightStatusBars = !isSystemInDarkTheme
        isAppearanceLightNavigationBars = !isSystemInDarkTheme
    }
}
