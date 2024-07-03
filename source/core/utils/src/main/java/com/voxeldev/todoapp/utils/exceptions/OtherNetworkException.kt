package com.voxeldev.todoapp.utils.exceptions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.voxeldev.todoapp.utils.R
import com.voxeldev.todoapp.utils.exceptions.base.DisplayableException

/**
 * @author nvoxel
 */
class OtherNetworkException : DisplayableException, Throwable() {

    @Composable
    override fun getDisplayMessage(): String = stringResource(R.string.other_network_exception)
}
