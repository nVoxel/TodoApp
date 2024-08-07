package com.voxeldev.todoapp.plugin.stats

import com.voxeldev.todoapp.plugin.BuildVerifyPlugin.Companion.APK_SIZE_FILE_NAME
import com.voxeldev.todoapp.plugin.BuildVerifyPlugin.Companion.BUILD_VERIFY_TAG
import com.voxeldev.todoapp.telegram.TelegramApi
import com.voxeldev.todoapp.utils.APK_SUFFIX
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.inject.Inject

/**
 * @author nvoxel
 */
internal abstract class TelegramFileTask @Inject constructor(
    private val telegramApi: TelegramApi,
    private val buildVariant: String,
    private val versionCode: String,
) : DefaultTask() {

    @get:InputDirectory
    abstract val apkDir: DirectoryProperty

    @get:Input
    @get:Optional
    abstract val sendFile: Property<Boolean>

    @get:Input
    abstract val chatId: Property<String>

    @get:Input
    abstract val token: Property<String>

    @TaskAction
    fun report() {
        val sendEnabled = sendFile.getOrElse(true)

        val apkDirFile = apkDir.get().asFile

        val token = token.get()
        val chatId = chatId.get()

        val resultFileName = "$FILENAME_PREFIX-$buildVariant-$versionCode.apk"

        apkDirFile.listFiles()
            ?.filter { file -> file.name.endsWith(APK_SUFFIX) }
            ?.forEach { file ->
                val formattedSize = File(apkDirFile, APK_SIZE_FILE_NAME).readText()

                if (!sendEnabled) return

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
