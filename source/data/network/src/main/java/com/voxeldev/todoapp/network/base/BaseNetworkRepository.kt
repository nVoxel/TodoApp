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
    ): Result<Model> =
        getResponse(request = request).fold(
            onSuccess = { response ->
                return runCatching {
                    val body: Response = response.body() ?: return Result.failure(UnexpectedResponseException())
                    return@runCatching body.transform()
                }
            },
            onFailure = { exception -> return Result.failure(exception = exception) },
        )

    private suspend fun getResponse(
        request: HttpStatement,
    ): Result<HttpResponse> {
        networkHandler.isNetworkAvailable().let { isNetworkAvailable ->
            if (!isNetworkAvailable) {
                return Result.failure(NetworkNotAvailableException())
            }
        }

        return runCatching {
            val response = request.execute()

            if (response.status != HttpStatusCode.OK) {
                return when (response.status) {
                    HttpStatusCode.Unauthorized -> Result.failure(TokenNotFoundException())
                    HttpStatusCode.InternalServerError -> Result.failure(ServerErrorException())
                    else -> Result.failure(OtherNetworkException(responseCode = response.status.value))
                }
            }

            return Result.success(response)
        }
    }
}
