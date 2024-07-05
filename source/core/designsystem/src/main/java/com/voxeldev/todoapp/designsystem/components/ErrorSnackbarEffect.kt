package com.voxeldev.todoapp.designsystem.components

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

/**
 * @author nvoxel
 */
@Composable
fun ErrorSnackbarEffect(
    errorMessage: String?,
    snackbarHostState: SnackbarHostState,
    onHide: () -> Unit,
) {
    LaunchedEffect(key1 = errorMessage) {
        if (errorMessage == null) return@LaunchedEffect
        snackbarHostState.showSnackbar(
            message = errorMessage,
            duration = SnackbarDuration.Short,
        )
        onHide()
    }
}
