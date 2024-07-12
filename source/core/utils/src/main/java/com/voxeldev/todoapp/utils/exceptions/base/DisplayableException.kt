package com.voxeldev.todoapp.utils.exceptions.base

import androidx.compose.runtime.Composable

/**
 * Marks exceptions that can display user-friendly messages.
 * @author nvoxel
 */
interface DisplayableException {
    @Composable
    fun getDisplayMessage(): String
}
