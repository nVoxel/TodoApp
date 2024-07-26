package com.voxeldev.todoapp.di.stubs.utils

import com.voxeldev.todoapp.utils.platform.NetworkHandler
import javax.inject.Inject

/**
 * @author nvoxel
 */
class StubNetworkHandler @Inject constructor() : NetworkHandler {

    override fun isNetworkAvailable(): Boolean = true
}
