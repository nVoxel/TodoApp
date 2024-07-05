package com.voxeldev.todoapp.utils.exceptions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.voxeldev.todoapp.utils.R
import com.voxeldev.todoapp.utils.exceptions.base.DisplayableException

/**
 * Means app is unable to parse stored [AuthToken].
 * @author nvoxel
 */
class CorruptedTokenException : DisplayableException, Exception() {

    @Composable
    override fun getDisplayMessage(): String = stringResource(R.string.corrupted_token_exception)
}
