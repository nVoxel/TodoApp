package com.voxeldev.todoapp.utils.platform

import kotlinx.coroutines.flow.StateFlow

/**
 * @author nvoxel
 */
interface NetworkObserver {

    val networkAvailability: StateFlow<Boolean>
}
