package com.voxeldev.todoapp.utils.extensions

/**
 * @author nvoxel
 */
internal fun String.capitalized(): String = "${this[0].uppercase()}${substring(1)}"
