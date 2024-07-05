package com.voxeldev.todoapp.utils.exceptions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.voxeldev.todoapp.utils.R
import com.voxeldev.todoapp.utils.exceptions.base.DisplayableException

/**
 * Means stored revision does not exist.
 * @author nvoxel
 */
class RevisionNotAvailableException : DisplayableException, Exception() {

    @Composable
    override fun getDisplayMessage(): String = stringResource(R.string.revision_not_available_exception)
}
