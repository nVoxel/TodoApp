package com.voxeldev.todoapp.utils.exceptions.base

import androidx.compose.runtime.Composable

/**
 * @author nvoxel
 */
interface DisplayableException {
    @Composable
    fun getDisplayMessage(): String
}
