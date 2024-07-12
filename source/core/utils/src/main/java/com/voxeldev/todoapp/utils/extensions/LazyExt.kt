package com.voxeldev.todoapp.utils.extensions

/**
 * @author nvoxel
 */
fun <T> lazyUnsafe(initializer: () -> T) = lazy(mode = LazyThreadSafetyMode.NONE, initializer = initializer)
