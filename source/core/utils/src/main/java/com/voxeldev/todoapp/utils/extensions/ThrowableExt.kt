package com.voxeldev.todoapp.utils.extensions

/**
 * @author nvoxel
 */
fun Throwable.getMessage() = message ?: toString()
