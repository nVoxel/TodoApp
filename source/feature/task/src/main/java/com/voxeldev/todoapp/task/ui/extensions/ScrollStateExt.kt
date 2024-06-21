package com.voxeldev.todoapp.task.ui.extensions

import androidx.compose.foundation.ScrollState

/**
 * @author nvoxel
 */
fun ScrollState.scrollPercentage(): Float {
    val max = if (maxValue > 0) maxValue.toFloat() else DEFAULT_SCROLL_VALUE
    return value / max
}

private const val DEFAULT_SCROLL_VALUE = 1F
