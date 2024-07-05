package com.voxeldev.todoapp.auth.exceptions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.voxeldev.todoapp.auth.R
import com.voxeldev.todoapp.utils.exceptions.base.DisplayableException

/**
 * Means authentication attempt failed.
 * @author nvoxel
 */
internal class FailedToAuthenticateException : DisplayableException, Exception() {

    @Composable
    override fun getDisplayMessage(): String = stringResource(id = R.string.failed_to_authenticate_exception)
}
