package com.voxeldev.todoapp.plugin.stats

import com.voxeldev.todoapp.plugin.BuildVerifyPlugin.Companion.BUILD_VERIFY_TAG
import com.voxeldev.todoapp.plugin.size.ValidateApkSizeTask.Companion.APK_SIZE_FILE_NAME
import com.voxeldev.todoapp.telegram.TelegramApi
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.inject.Inject

/**
 * @author nvoxel
 */
abstract class TelegramStatsTask @Inject constructor(
    private val telegramApi: TelegramApi,
    private val buildVariant: String,
    private val versionCode: String,
) : DefaultTask() {

    @get:InputDirectory
    abstract val apkDir: DirectoryProperty

    @get:Input
    abstract val token: Property<String>

    @get:Input
    abstract val chatId: Property<String>

    @TaskAction
    fun report() {
        val apkDirFile = apkDir.get().asFile

        val token = token.get()
        val chatId = chatId.get()

        val resultFileName = "$FILENAME_PREFIX-$buildVariant-$versionCode.apk"

        apkDirFile.listFiles()
            ?.filter { file -> file.name.endsWith(".apk") }
            ?.forEach { file ->
                val formattedSize = File(apkDirFile, APK_SIZE_FILE_NAME).readText()

                runBlocking {
                    telegramApi.sendMessage(
                        message = "Build finished... Expected APK size is: $formattedSize MB",
                        token = token,
                        chatId = chatId,
                    )
                }

                runBlocking {
                    telegramApi.sendFile(
                        file = file,
                        filename = resultFileName,
                        chatId = chatId,
                        token = token,
                    ).apply {
                        if (status == HttpStatusCode.OK) {
                            println("[$BUILD_VERIFY_TAG] Sent APK to $chatId successfully")
                        } else {
                            println("[$BUILD_VERIFY_TAG] Failed to send APK. Status code: ${status.value}")
                        }
                    }
                }
            }
    }

    private companion object {
        const val FILENAME_PREFIX = "todolist"
    }
}
