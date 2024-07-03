package com.voxeldev.todoapp.utils.providers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * @author nvoxel
 */
class CoroutineDispatcherProviderDefaultImpl @Inject constructor() : CoroutineDispatcherProvider {

    override val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
    override val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
    override val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
}
