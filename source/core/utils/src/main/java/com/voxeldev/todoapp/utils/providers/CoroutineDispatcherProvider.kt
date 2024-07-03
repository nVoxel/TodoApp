package com.voxeldev.todoapp.utils.providers

import kotlinx.coroutines.CoroutineDispatcher

/**
 * @author nvoxel
 */
interface CoroutineDispatcherProvider {
    val defaultDispatcher: CoroutineDispatcher
    val mainDispatcher: CoroutineDispatcher
    val ioDispatcher: CoroutineDispatcher
}
