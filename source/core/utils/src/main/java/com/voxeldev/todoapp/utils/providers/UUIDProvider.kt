package com.voxeldev.todoapp.utils.providers

import java.util.UUID

/**
 * @author nvoxel
 */
object UUIDProvider {

    fun provideUUID(): String = UUID.randomUUID().toString()
}
