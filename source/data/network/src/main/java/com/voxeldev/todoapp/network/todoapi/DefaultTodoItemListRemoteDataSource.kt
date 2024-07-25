package com.voxeldev.todoapp.network.todoapi

import com.voxeldev.todoapp.network.base.BaseNetworkRepository
import com.voxeldev.todoapp.network.todoapi.datasource.URIs.LIST_URI
import com.voxeldev.todoapp.network.todoapi.datasource.response.TodoListResponse
import com.voxeldev.todoapp.utils.platform.NetworkHandler
import io.ktor.client.HttpClient
import io.ktor.client.request.prepareGet
import io.ktor.client.request.url
import javax.inject.Inject

/**
 * [TodoItemListRemoteDataSource] default implementation that uses ktor.
 * @author nvoxel
 */
internal class DefaultTodoItemListRemoteDataSource @Inject constructor(
    networkHandler: NetworkHandler,
    private val httpClient: HttpClient,
) : TodoItemListRemoteDataSource, BaseNetworkRepository<TodoListResponse>(networkHandler = networkHandler) {

    override suspend fun getAll(): Result<TodoListResponse> =
        doRequest<TodoListResponse>(
            request = httpClient.prepareGet {
                url(urlString = LIST_URI)
            },
            transform = { return@doRequest this },
        )
}
