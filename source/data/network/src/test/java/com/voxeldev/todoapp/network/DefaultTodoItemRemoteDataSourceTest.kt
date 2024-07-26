package com.voxeldev.todoapp.network

import com.voxeldev.todoapp.network.base.RemoteDataSourceTest
import com.voxeldev.todoapp.network.todoapi.DefaultTodoItemRemoteDataSource
import com.voxeldev.todoapp.network.todoapi.datasource.response.TodoSingleResponse
import com.voxeldev.todoapp.network.todoapi.mapper.TodoItemRequestMapper
import com.voxeldev.todoapp.network.utils.loadResourceAsString
import com.voxeldev.todoapp.utils.exceptions.NetworkNotAvailableException
import com.voxeldev.todoapp.utils.exceptions.OtherNetworkException
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.fullPath
import io.ktor.http.headersOf
import io.ktor.serialization.JsonConvertException
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * @author nvoxel
 */
class DefaultTodoItemRemoteDataSourceTest : RemoteDataSourceTest() {

    @Test
    fun `DefaultTodoItemRemoteDataSource returns same item`() = runTest {
        val taskId = "first_task"

        val responseString = loadResourceAsString(clazz = this, fileName = "single_item.json")
        val expectedResult = json.decodeFromString<TodoSingleResponse>(string = responseString)

        val httpClient = getHttpClient { requestData ->
            assertTrue(message = "The last path segment should be the task id") {
                requestData.url.fullPath.endsWith(taskId)
            }

            respond(
                content = responseString,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }

        val dataSource = DefaultTodoItemRemoteDataSource(
            networkHandler = availableNetworkHandler,
            httpClient = httpClient,
            todoItemRequestMapper = TodoItemRequestMapper(),
        )

        val dataSourceResult = dataSource.getSingle(id = taskId)

        assertTrue(
            message = "DataSource should return success. Instead got: ${dataSourceResult.exceptionOrNull()?.message}",
        ) { dataSourceResult.isSuccess }

        assertEquals(
            expected = expectedResult,
            actual = dataSourceResult.getOrThrow(),
            message = "DataSource should return the same item",
        )
    }

    @Test
    fun `DefaultTodoItemRemoteDataSource returns exception on wrong formatted response`() = runTest {
        val httpClient = getHttpClient {
            respond(
                content = "wrong format",
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }

        val dataSource = DefaultTodoItemRemoteDataSource(
            networkHandler = availableNetworkHandler,
            httpClient = httpClient,
            todoItemRequestMapper = TodoItemRequestMapper(),
        )

        val dataSourceResult = dataSource.getSingle(id = "task")

        assertTrue(message = "DataSource should return Result.failure with JsonConvertException") {
            dataSourceResult.isFailure && dataSourceResult.exceptionOrNull()!! is JsonConvertException
        }
    }

    @Test
    fun `DefaultTodoItemRemoteDataSource returns exception on non-400 status code`() = runTest {
        val responseString = loadResourceAsString(clazz = this, fileName = "single_item.json")

        val httpClient = getHttpClient {
            respond(
                content = responseString,
                status = HttpStatusCode.NotFound,
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }

        val dataSource = DefaultTodoItemRemoteDataSource(
            networkHandler = availableNetworkHandler,
            httpClient = httpClient,
            todoItemRequestMapper = TodoItemRequestMapper(),
        )

        val dataSourceResult = dataSource.getSingle(id = "task")
        assertTrue(message = "DataSource should return Result.failure with OtherNetworkException") {
            dataSourceResult.isFailure && dataSourceResult.exceptionOrNull()!! is OtherNetworkException
        }

        val exception = dataSourceResult.exceptionOrNull()!! as OtherNetworkException
        assertTrue(message = "DataSource should return OtherNetworkException with 404 status code") {
            exception.responseCode == HttpStatusCode.NotFound.value
        }
    }

    @Test
    fun `DefaultTodoItemRemoteDataSource returns exception on unavailable network`() = runTest {
        val responseString = loadResourceAsString(clazz = this, fileName = "single_item.json")

        val httpClient = getHttpClient {
            respond(
                content = responseString,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }

        val dataSource = DefaultTodoItemRemoteDataSource(
            networkHandler = unavailableNetworkHandler,
            httpClient = httpClient,
            todoItemRequestMapper = TodoItemRequestMapper(),
        )

        val dataSourceResult = dataSource.getSingle(id = "task")

        assertTrue(message = "DataSource should return Result.failure with NetworkNotAvailableException") {
            dataSourceResult.isFailure && dataSourceResult.exceptionOrNull()!! is NetworkNotAvailableException
        }
    }
}
