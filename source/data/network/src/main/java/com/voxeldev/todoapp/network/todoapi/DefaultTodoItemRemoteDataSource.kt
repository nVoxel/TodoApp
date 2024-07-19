package com.voxeldev.todoapp.network.todoapi

import com.voxeldev.todoapp.api.request.TodoItemModifyRequest
import com.voxeldev.todoapp.network.base.BaseNetworkRepository
import com.voxeldev.todoapp.network.todoapi.datasource.URIs.LIST_URI
import com.voxeldev.todoapp.network.todoapi.datasource.response.TodoSingleResponse
import com.voxeldev.todoapp.network.todoapi.mapper.TodoItemRequestMapper
import com.voxeldev.todoapp.utils.extensions.url
import com.voxeldev.todoapp.utils.platform.NetworkHandler
import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.prepareDelete
import io.ktor.client.request.prepareGet
import io.ktor.client.request.preparePost
import io.ktor.client.request.preparePut
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import javax.inject.Inject

/**
 * [TodoItemRemoteDataSource] default implementation that uses ktor.
 * @author nvoxel
 */
internal class DefaultTodoItemRemoteDataSource @Inject constructor(
    networkHandler: NetworkHandler,
    private val httpClient: HttpClient,
    private val todoItemRequestMapper: TodoItemRequestMapper,
) : TodoItemRemoteDataSource, BaseNetworkRepository<TodoSingleResponse>(networkHandler = networkHandler) {

    override suspend fun createItem(
        request: TodoItemModifyRequest,
        deviceId: String,
        revision: Int,
    ): Result<Unit> =
        doRequest(
            request = httpClient.preparePost {
                url(urlString = LIST_URI)

                contentType(ContentType.Application.Json)
                setBody(
                    todoItemRequestMapper.toRequest(
                        model = request,
                        deviceId = deviceId,
                    ),
                )

                headers {
                    append(REVISION_HEADER_NAME, revision.toString())
                }
            },
        )

    override suspend fun getSingle(id: String): Result<TodoSingleResponse> =
        doRequest<TodoSingleResponse>(
            request = httpClient.prepareGet {
                url(urlString = LIST_URI) {
                    appendPathSegments(id)
                }
            },
            transform = { return@doRequest this },
        )

    override suspend fun updateItem(
        request: TodoItemModifyRequest,
        deviceId: String,
        revision: Int,
    ): Result<Unit> =
        doRequest(
            request = httpClient.preparePut {
                url(urlString = LIST_URI) {
                    appendPathSegments(request.id)
                }

                contentType(ContentType.Application.Json)
                setBody(
                    todoItemRequestMapper.toRequest(
                        model = request,
                        deviceId = deviceId,
                    ),
                )

                headers {
                    append(REVISION_HEADER_NAME, revision.toString())
                }
            },
        )

    override suspend fun deleteItem(id: String, revision: Int): Result<Unit> =
        doRequest(
            request = httpClient.prepareDelete {
                url(urlString = LIST_URI) {
                    appendPathSegments(id)
                }

                headers {
                    append(REVISION_HEADER_NAME, revision.toString())
                }
            },
        )

    private companion object {
        const val REVISION_HEADER_NAME = "X-Last-Known-Revision"
    }
}
