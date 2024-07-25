package com.voxeldev.todoapp.network

import com.voxeldev.todoapp.network.base.RemoteDataSourceTest
import com.voxeldev.todoapp.network.todoapi.DefaultTodoItemListRemoteDataSource
import com.voxeldev.todoapp.network.todoapi.datasource.response.TodoListResponse
import com.voxeldev.todoapp.network.utils.loadResourceAsString
import com.voxeldev.todoapp.utils.exceptions.NetworkNotAvailableException
import com.voxeldev.todoapp.utils.exceptions.OtherNetworkException
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.JsonConvertException
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * @author nvoxel
 */
class DefaultTodoItemListRemoteDataSourceTest : RemoteDataSourceTest() {

    @Test
    fun `DefaultTodoItemListRemoteDataSource returns no items`() = runTest {
        val responseString = loadResourceAsString(clazz = this, fileName = "list_with_no_items.json")

        val httpClient = getHttpClient {
            respond(
                content = responseString,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }

        val dataSource = DefaultTodoItemListRemoteDataSource(
            networkHandler = availableNetworkHandler,
            httpClient = httpClient,
        )

        val result = dataSource.getAll()

        assertTrue(message = "DataSource should return no items") {
            result.isSuccess && result.getOrNull()!!.list.isEmpty()
        }
    }

    @Test
    fun `DefaultTodoItemListRemoteDataSource returns same items`() = runTest {
        val responseString = loadResourceAsString(clazz = this, fileName = "list_with_multiple_items.json")
        val expectedResult = json.decodeFromString<TodoListResponse>(string = responseString)

        val httpClient = getHttpClient {
            respond(
                content = responseString,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }

        val dataSource = DefaultTodoItemListRemoteDataSource(
            networkHandler = availableNetworkHandler,
            httpClient = httpClient,
        )

        val repositoryResult = dataSource.getAll()

        assertTrue(message = "DataSource should return success") {
            repositoryResult.isSuccess
        }

        assertEquals(
            expected = expectedResult,
            actual = repositoryResult.getOrNull(),
            message = "DataSource should return all items",
        )
    }

    @Test
    fun `DefaultTodoItemListRemoteDataSource returns exception on wrong formatted response`() = runTest {
        val httpClient = getHttpClient {
            respond(
                content = "wrong format",
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }

        val dataSource = DefaultTodoItemListRemoteDataSource(
            networkHandler = availableNetworkHandler,
            httpClient = httpClient,
        )

        val result = dataSource.getAll()

        assertTrue(message = "DataSource should return Result.failure with JsonConvertException") {
            result.isFailure && result.exceptionOrNull()!! is JsonConvertException
        }
    }

    @Test
    fun `DefaultTodoItemListRemoteDataSource returns exception on non-400 status code`() = runTest {
        val responseString = loadResourceAsString(clazz = this, fileName = "list_with_no_items.json")

        val httpClient = getHttpClient {
            respond(
                content = responseString,
                status = HttpStatusCode.BadGateway,
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }

        val dataSource = DefaultTodoItemListRemoteDataSource(
            networkHandler = availableNetworkHandler,
            httpClient = httpClient,
        )

        val result = dataSource.getAll()
        assertTrue(message = "DataSource should return Result.failure with OtherNetworkException") {
            result.isFailure && result.exceptionOrNull()!! is OtherNetworkException
        }

        val exception = result.exceptionOrNull()!! as OtherNetworkException
        assertTrue(message = "DataSource should return OtherNetworkException with 502 status code") {
            exception.responseCode == HttpStatusCode.BadGateway.value
        }
    }

    @Test
    fun `DefaultTodoItemListRemoteDataSource returns exception on unavailable network`() = runTest {
        val responseString = loadResourceAsString(clazz = this, fileName = "list_with_no_items.json")

        val httpClient = getHttpClient {
            respond(
                content = responseString,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }

        val dataSource = DefaultTodoItemListRemoteDataSource(
            networkHandler = unavailableNetworkHandler,
            httpClient = httpClient,
        )

        val result = dataSource.getAll()

        assertTrue(message = "DataSource should return Result.failure with NetworkNotAvailableException") {
            result.isFailure && result.exceptionOrNull()!! is NetworkNotAvailableException
        }
    }
}
