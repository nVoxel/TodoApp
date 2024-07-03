package com.voxeldev.todoapp.network.mapper

/**
 * @author nvoxel
 */
internal interface RequestMapper<Model, Request> {
    fun toRequest(model: Model): Request
}
