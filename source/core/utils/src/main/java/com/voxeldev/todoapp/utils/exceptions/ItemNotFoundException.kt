package com.voxeldev.todoapp.utils.exceptions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.voxeldev.todoapp.utils.R
import com.voxeldev.todoapp.utils.exceptions.base.DisplayableException

/**
 * Means task with specified [id] does not exist.
 * @author nvoxel
 */
class ItemNotFoundException(private val id: String) : DisplayableException, Exception() {

    @Composable
    override fun getDisplayMessage(): String = stringResource(R.string.item_not_found_exception, id)
}
