package com.voxeldev.todoapp.network.base

import com.voxeldev.todoapp.utils.exceptions.NetworkNotAvailableException
import com.voxeldev.todoapp.utils.exceptions.OtherNetworkException
import com.voxeldev.todoapp.utils.exceptions.ServerErrorException
import com.voxeldev.todoapp.utils.exceptions.TokenNotFoundException
import com.voxeldev.todoapp.utils.exceptions.UnexpectedResponseException
import com.voxeldev.todoapp.utils.platform.NetworkHandler
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.HttpStatement
import io.ktor.http.HttpStatusCode

/**
 * Base network repository that uses ktor and executes HTTP requests.
 * @author nvoxel
 */
internal abstract class BaseNetworkRepository<Model>(
    protected val networkHandler: NetworkHandler,
) {

    /**
     * Executes request without parsing response
     */
    protected suspend fun doRequest(
        request: HttpStatement,
    ): Result<Unit> =
        getResponse(request = request).fold(
            onSuccess = { return Result.success(Unit) },
            onFailure = { exception -> return Result.failure(exception = exception) },
        )

    /**
     * Executes request and parses response
     */
    protected suspend inline fun <reified Response> doRequest(
        request: HttpStatement,
        transform: Response.() -> Model,
    ): Result<Model> = getResponse(request = request).fold(
        onSuccess = { response -> return transformBody(response, transform) },
        onFailure = { exception -> return Result.failure(exception = exception) },
    )

    private suspend fun getResponse(
        request: HttpStatement,
    ): Result<HttpResponse> {
        if (!networkHandler.isNetworkAvailable()) {
            return Result.failure(NetworkNotAvailableException())
        }

        return runCatching {
            val response = request.execute()
            checkResponse(response = response)
            return Result.success(response)
        }
    }

    private fun checkResponse(response: HttpResponse) {
        if (response.status == HttpStatusCode.OK) return

        val exception = when (response.status) {
            HttpStatusCode.Unauthorized -> TokenNotFoundException()
            HttpStatusCode.InternalServerError -> ServerErrorException()
            else -> OtherNetworkException(responseCode = response.status.value)
        }

        throw exception
    }

    private suspend inline fun <reified Response> transformBody(
        response: HttpResponse,
        transform: Response.() -> Model,
    ) = runCatching {
        val body: Response = response.body() ?: return Result.failure<Model>(UnexpectedResponseException())
        return@runCatching body.transform()
    }
}
