package com.voxeldev.todoapp.designsystem.theme.ripple

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette

/**
 * @author nvoxel
 */
internal object CheckRippleTheme : RippleTheme {

    private const val RIPPLE_ALPHA = 0.5f

    @Composable
    override fun defaultColor(): Color = LocalAppPalette.current.colorGreen

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(
        draggedAlpha = RIPPLE_ALPHA,
        focusedAlpha = RIPPLE_ALPHA,
        hoveredAlpha = RIPPLE_ALPHA,
        pressedAlpha = RIPPLE_ALPHA,
    )
}
