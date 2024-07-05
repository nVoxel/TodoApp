package com.voxeldev.todoapp.utils.extensions

import androidx.compose.runtime.Composable
import com.voxeldev.todoapp.utils.exceptions.base.DisplayableException

/**
 * @author nvoxel
 */
@Composable
fun Exception.getDisplayMessage(): String = if (this is DisplayableException) getDisplayMessage() else message ?: toString()
