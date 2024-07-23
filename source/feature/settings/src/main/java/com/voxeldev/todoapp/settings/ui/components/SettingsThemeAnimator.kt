package com.voxeldev.todoapp.settings.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import com.voxeldev.todoapp.api.model.AppTheme
import com.voxeldev.todoapp.settings.ui.INITIAL_PROGRESS
import com.voxeldev.todoapp.settings.ui.TARGET_PROGRESS
import com.voxeldev.todoapp.settings.ui.TRANSITION_DURATION_MILLIS

/**
 * @author nvoxel
 */
internal suspend fun animateNewTheme(
    selectedAppTheme: AppTheme,
    newAppTheme: AppTheme,
    isSystemInDarkTheme: Boolean,
    transitionProgress: Animatable<Float, AnimationVector1D>,
    onThemeChanged: (AppTheme) -> Unit,
) {
    val shouldAnimateNewTheme = shouldAnimateNewTheme(
        previousTheme = selectedAppTheme,
        nextTheme = newAppTheme,
        isSystemInDarkTheme = isSystemInDarkTheme,
    )

    if (shouldAnimateNewTheme) {
        transitionProgress.snapTo(
            targetValue = TARGET_PROGRESS,
        )
    }

    onThemeChanged(newAppTheme)

    if (shouldAnimateNewTheme) {
        transitionProgress.animateTo(
            targetValue = INITIAL_PROGRESS,
            animationSpec = tween(durationMillis = TRANSITION_DURATION_MILLIS),
        )
    }
}

private fun shouldAnimateNewTheme(
    previousTheme: AppTheme,
    nextTheme: AppTheme,
    isSystemInDarkTheme: Boolean,
): Boolean {
    val actualPreviousTheme = if (previousTheme == AppTheme.Auto) {
        if (isSystemInDarkTheme) AppTheme.Dark else AppTheme.Light
    } else {
        previousTheme
    }

    val actualNextTheme = if (nextTheme == AppTheme.Auto) {
        if (isSystemInDarkTheme) AppTheme.Dark else AppTheme.Light
    } else {
        nextTheme
    }

    return actualPreviousTheme != actualNextTheme
}
