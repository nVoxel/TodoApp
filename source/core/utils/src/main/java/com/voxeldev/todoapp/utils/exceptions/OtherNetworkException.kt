package com.voxeldev.todoapp.utils.exceptions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.voxeldev.todoapp.utils.R
import com.voxeldev.todoapp.utils.exceptions.base.DisplayableException

/**
 * Means the backend answered with non-200 response code.
 * @author nvoxel
 */
class OtherNetworkException(private val responseCode: Int) : DisplayableException, Exception() {

    @Composable
    override fun getDisplayMessage(): String = stringResource(R.string.other_network_exception, responseCode)
}