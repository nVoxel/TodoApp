package com.voxeldev.todoapp.domain.base

import com.voxeldev.todoapp.utils.providers.CoroutineDispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher

/**
 * @author nvoxel
 */
open class UseCaseTest {

    protected val testDispatcher = StandardTestDispatcher()

    protected val coroutineDispatcherProvider = object : CoroutineDispatcherProvider {
        override val defaultDispatcher: CoroutineDispatcher = testDispatcher
        override val mainDispatcher: CoroutineDispatcher = testDispatcher
        override val ioDispatcher: CoroutineDispatcher = testDispatcher
    }
}
