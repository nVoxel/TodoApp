package com.voxeldev.todoapp.task.ui.extensions

import androidx.compose.foundation.ScrollState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private const val DEFAULT_SCROLL_VALUE = 1F

private const val DEFAULT_MAX_ELEVATION_DP = 8
private const val DEFAULT_THRESHOLD_SCROLL_PERCENTAGE = 0.2f

/**
 * @author nvoxel
 */
private fun ScrollState.scrollPercentage(): Float {
    val max = if (maxValue > 0) maxValue.toFloat() else DEFAULT_SCROLL_VALUE
    return value / max
}

internal fun ScrollState.calculateTopBarElevation(
    maxElevation: Dp = DEFAULT_MAX_ELEVATION_DP.dp,
    thresholdScrollPercentage: Float = DEFAULT_THRESHOLD_SCROLL_PERCENTAGE,
): Dp {
    val scrollPercentage = scrollPercentage()
    val animateElevation = scrollPercentage < thresholdScrollPercentage

    return if (animateElevation) {
        maxElevation * (scrollPercentage / thresholdScrollPercentage)
    } else {
        maxElevation
    }
}
