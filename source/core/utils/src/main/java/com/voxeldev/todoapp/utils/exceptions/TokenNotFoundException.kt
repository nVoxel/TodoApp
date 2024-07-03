package com.voxeldev.todoapp.utils.exceptions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.voxeldev.todoapp.utils.R
import com.voxeldev.todoapp.utils.exceptions.base.DisplayableException

/**
 * @author nvoxel
 */
class TokenNotFoundException : DisplayableException, Throwable() {

    @Composable
    override fun getDisplayMessage(): String = stringResource(R.string.token_not_found_exception)
}
