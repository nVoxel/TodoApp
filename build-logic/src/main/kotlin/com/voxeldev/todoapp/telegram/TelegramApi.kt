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
import java.io.File

private const val TELEGRAM_API_URL = "https://api.telegram.org"

/**
 * @author nvoxel
 */
internal class TelegramApi(private val httpClient: HttpClient) {

    suspend fun sendFile(
        file: File,
        filename: String,
        chatId: String,
        token: String,
    ): HttpResponse = httpClient.post("$TELEGRAM_API_URL/bot$token/sendDocument") {
        parameter("chat_id", chatId)

        setBody(fileToMultipart(file = file, filename = filename))
    }

    suspend fun sendMessage(
        message: String,
        chatId: String,
        token: String,
        useMarkdownV2: Boolean = false,
    ): HttpResponse = httpClient.post("$TELEGRAM_API_URL/bot$token/sendMessage") {
        parameter("chat_id", chatId)
        parameter("text", message)
        if (useMarkdownV2) {
            parameter("parse_mode", "MarkdownV2")
        }
    }

    private fun fileToMultipart(
        file: File,
        filename: String,
    ): MultiPartFormDataContent =
        MultiPartFormDataContent(
            parts = formData {
                append(
                    key = "document",
                    value = file.readBytes(),
                    headers = Headers.build {
                        append(HttpHeaders.ContentDisposition, "filename=$filename")
                    },
                )
            },
        )
}
