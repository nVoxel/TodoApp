package com.voxeldev.todoapp.network.mapper

/**
 * Maps [Response] to [Model].
 * @author nvoxel
 */
internal interface ResponseMapper<Response, Model> {
    fun toModel(response: Response): Model
}
