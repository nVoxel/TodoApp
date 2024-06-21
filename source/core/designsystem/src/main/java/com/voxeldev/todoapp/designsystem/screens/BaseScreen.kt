package com.voxeldev.todoapp.designsystem.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.voxeldev.todoapp.designsystem.components.Error
import com.voxeldev.todoapp.designsystem.components.Loader
import com.voxeldev.todoapp.utils.base.BaseViewModel
import com.voxeldev.todoapp.utils.extensions.getMessage

/**
 * @author nvoxel
 */
@Composable
fun BaseScreen(
    viewModel: BaseViewModel,
    retryCallback: () -> Unit,
    content: @Composable () -> Unit,
) {
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.exception.collectAsState()

    error?.let {
        Error(
            message = it.getMessage(),
            shouldShowRetry = true,
            retryCallback = retryCallback,
        )
    } ?: run {
        if (loading) {
            Loader()
        } else {
            content()
        }
    }
}
