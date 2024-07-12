package com.voxeldev.todoapp.utils.exceptions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.voxeldev.todoapp.utils.R
import com.voxeldev.todoapp.utils.exceptions.base.DisplayableException
import io.ktor.http.HttpStatusCode

/**
 * Means the backend answered with non-200 response code.
 * @author nvoxel
 */
class OtherNetworkException(val responseCode: Int) : DisplayableException, Exception() {

    @Composable
    override fun getDisplayMessage(): String =
        when (responseCode) {
            HttpStatusCode.BadRequest.value -> stringResource(R.string.refreshing_data_exception)
            else -> stringResource(R.string.other_network_exception, responseCode)
        }
}
