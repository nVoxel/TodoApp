package com.voxeldev.todoapp.di.stubs.utils

import com.voxeldev.todoapp.utils.platform.NetworkObserver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * @author nvoxel
 */
class StubNetworkObserver @Inject constructor() : NetworkObserver {

    private val _networkAvailability: MutableStateFlow<Boolean> = MutableStateFlow(value = true)
    override val networkAvailability: StateFlow<Boolean> = _networkAvailability.asStateFlow()
}
