package com.voxeldev.todoapp.designsystem.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.voxeldev.todoapp.designsystem.components.FullscreenError
import com.voxeldev.todoapp.designsystem.components.FullscreenLoader
import com.voxeldev.todoapp.designsystem.components.TodoNetworkNotification
import com.voxeldev.todoapp.utils.base.BaseViewModel
import com.voxeldev.todoapp.utils.extensions.getDisplayMessage

/**
 * @author nvoxel
 */
@Composable
fun BaseScreen(
    viewModel: BaseViewModel,
    displayFullscreenError: Boolean = true,
    retryCallback: () -> Unit,
    content: @Composable () -> Unit,
) {
    val loading by viewModel.loading.collectAsStateWithLifecycle()
    val error by viewModel.exception.collectAsStateWithLifecycle()
    val networkNotification by viewModel.networkNotification.collectAsStateWithLifecycle()

    if (error != null && displayFullscreenError) {
        FullscreenError(
            message = error!!.getDisplayMessage(),
            shouldShowRetry = true,
            retryCallback = retryCallback,
        )
    } else if (loading) {
        FullscreenLoader()
    } else {
        BaseScreenContent(
            content = content,
            networkNotification = networkNotification,
            onNetworkNotificationClicked = viewModel::hideNetworkNotification,
        )
    }
}

@Composable
private fun BaseScreenContent(
    content: @Composable () -> Unit,
    networkNotification: Boolean,
    onNetworkNotificationClicked: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
    ) {
        content()

        Box(modifier = Modifier.zIndex(zIndex = 1f)) {
            TodoNetworkNotification(
                isVisible = networkNotification,
                onClick = onNetworkNotificationClicked,
            )
        }
    }
}
