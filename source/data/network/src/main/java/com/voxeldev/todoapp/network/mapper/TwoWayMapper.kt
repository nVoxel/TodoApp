package com.voxeldev.todoapp.network.mapper

/**
 * Combines [RequestMapper] with [ResponseMapper].
 * @author nvoxel
 */
internal interface TwoWayMapper<Model, Request, Response> : RequestMapper<Model, Request>, ResponseMapper<Response, Model>
