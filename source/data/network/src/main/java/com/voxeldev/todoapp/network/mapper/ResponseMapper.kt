package com.voxeldev.todoapp.network.mapper

/**
 * @author nvoxel
 */
internal interface ResponseMapper<Response, Model> {
    fun toModel(response: Response): Model
}
