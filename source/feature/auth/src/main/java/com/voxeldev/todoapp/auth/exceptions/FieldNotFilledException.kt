package com.voxeldev.todoapp.auth.exceptions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.voxeldev.todoapp.auth.R
import com.voxeldev.todoapp.utils.exceptions.base.DisplayableException

/**
 * Means required form field is not filled.
 * @author nvoxel
 */
internal class FieldNotFilledException : DisplayableException, Exception() {

    @Composable
    override fun getDisplayMessage(): String = stringResource(id = R.string.field_not_filled_exception)
}
