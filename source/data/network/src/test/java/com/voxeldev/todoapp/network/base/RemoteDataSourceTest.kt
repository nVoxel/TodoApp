package com.voxeldev.todoapp.network.base

import com.voxeldev.todoapp.utils.platform.NetworkHandler
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

/**
 * @author nvoxel
 */
open class RemoteDataSourceTest {

    @OptIn(ExperimentalSerializationApi::class)
    protected val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    protected val availableNetworkHandler = mock<NetworkHandler> {
        on { isNetworkAvailable() } doReturn true
    }

    protected val unavailableNetworkHandler = mock<NetworkHandler> {
        on { isNetworkAvailable() } doReturn false
    }

    protected fun getHttpClient(
        rules: suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData,
    ): HttpClient = HttpClient(MockEngine(handler = rules)) {
        defaultRequest {
            url(API_BASE_URL)
        }

        install(ContentNegotiation) {
            json(json = json)
        }
    }

    protected companion object {
        const val API_BASE_URL = "https://localhost/"
    }
}
