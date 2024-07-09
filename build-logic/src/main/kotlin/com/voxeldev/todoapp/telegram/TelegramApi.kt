package com.voxeldev.todoapp.telegram

import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.escapeIfNeeded
import java.io.File

private const val TELEGRAM_API_URL = "https://api.telegram.org"

/**
 * @author nvoxel
 */
class TelegramApi(private val httpClient: HttpClient) {

    suspend fun sendFile(
        file: File,
        token: String,
        chatId: String,
    ): HttpResponse = httpClient.post("$TELEGRAM_API_URL/bot$token/sendDocument") {
        parameter("chat_id", chatId)

        setBody(fileToMultipart(file = file))
    }

    suspend fun sendMessage(
        message: String,
        token: String,
        chatId: String,
    ): HttpResponse = httpClient.post("$TELEGRAM_API_URL/bot$token/sendMessage") {
        parameter("chat_id", chatId)
        parameter("text", message)
    }

    private fun fileToMultipart(file: File): MultiPartFormDataContent =
        MultiPartFormDataContent(
            parts = formData {
                append(
                    key = "document",
                    value = file.readBytes(),
                    headers = Headers.build {
                        append(HttpHeaders.ContentDisposition, "filename=${file.name.escapeIfNeeded()}")
                    },
                )
            },
        )
}
