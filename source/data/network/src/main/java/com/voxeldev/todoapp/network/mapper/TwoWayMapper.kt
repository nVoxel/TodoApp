package com.voxeldev.todoapp.network.mapper

/**
 * @author nvoxel
 */
internal interface TwoWayMapper<Model, Request, Response> : RequestMapper<Model, Request>, ResponseMapper<Response, Model>
