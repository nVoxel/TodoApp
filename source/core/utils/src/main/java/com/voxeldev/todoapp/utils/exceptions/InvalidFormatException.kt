package com.voxeldev.todoapp.utils.exceptions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.voxeldev.todoapp.utils.R
import com.voxeldev.todoapp.utils.exceptions.base.DisplayableException

/**
 * Means app is unable to parse backend response.
 * @author nvoxel
 */
class InvalidFormatException : DisplayableException, Exception() {

    @Composable
    override fun getDisplayMessage(): String = stringResource(R.string.invalid_format_exception)
}
