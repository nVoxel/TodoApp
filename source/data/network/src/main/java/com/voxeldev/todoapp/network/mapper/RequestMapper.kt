package com.voxeldev.todoapp.network.mapper

/**
 * Maps [Model] to [Request].
 * @author nvoxel
 */
internal interface RequestMapper<Model, Request> {
    fun toRequest(model: Model): Request
}
