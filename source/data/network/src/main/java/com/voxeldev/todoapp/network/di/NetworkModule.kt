package com.voxeldev.todoapp.network.di

import com.voxeldev.todoapp.api.repository.AuthTokenRepository
import com.voxeldev.todoapp.utils.providers.StringResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import javax.inject.Singleton

/**
 * @author nvoxel
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideHttpClient(
        stringResourceProvider: StringResourceProvider,
        authTokenRepository: AuthTokenRepository,
    ): HttpClient = HttpClient(OkHttp) {
        defaultRequest {
            url(stringResourceProvider.getTodoApiBaseUrl())

            val token = authTokenRepository.getToken().getOrNull()
            token?.let {
                header(HttpHeaders.Authorization, "${token.type.headerPrefix} ${token.token}")
            }
        }

        install(ContentNegotiation) {
            json(
                json = Json {
                    ignoreUnknownKeys = true
                    explicitNulls = false
                },
            )
        }

        install(HttpRequestRetry) {
            maxRetries = MAX_RETRIES

            delayMillis { retry: Int ->
                retry * RETRY_EXP_DELAY_MILLIS
            }
        }
    }

    private companion object {
        const val MAX_RETRIES = 5
        const val RETRY_EXP_DELAY_MILLIS = 1000L
    }
}
