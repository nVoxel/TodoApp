package com.voxeldev.todoapp.plugin.size

import com.voxeldev.todoapp.telegram.TelegramApi
import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.util.Locale
import javax.inject.Inject

/**
 * @author nvoxel
 */
abstract class ValidateApkSizeTask @Inject constructor(
    private val telegramApi: TelegramApi,
) : DefaultTask() {

    @get:InputDirectory
    abstract val apkDir: DirectoryProperty

    @get:Input
    abstract val token: Property<String>

    @get:Input
    abstract val chatId: Property<String>

    @get:Input
    @get:Optional
    abstract val validationEnabled: Property<Boolean>

    @get:Input
    @get:Optional
    abstract val maxApkSizeMegabytes: Property<Int>

    @TaskAction
    fun validateApkSize() {
        val apkDirFile = apkDir.get().asFile

        val validationEnabled = validationEnabled.getOrElse(true)
        val maxApkSizeMegabytes = maxApkSizeMegabytes.getOrElse(DEFAULT_APK_SIZE_THRESHOLD)

        if (!validationEnabled || maxApkSizeMegabytes < 1) return

        val token = token.get()
        val chatId = chatId.get()

        apkDirFile.listFiles()
            ?.filter { file -> file.name.endsWith(".apk") }
            ?.forEach { file ->
                val fileSizeMegabytes = getFileSizeMegabytes(file = file)
                val formattedSize = String.format(Locale.getDefault(), "%.2f", fileSizeMegabytes)

                File(apkDirFile, APK_SIZE_FILE_NAME).writeText(text = formattedSize)

                if (fileSizeMegabytes >= maxApkSizeMegabytes) {
                    val message = "Build failed due to exceeding maximum apk size! " +
                            "Current apk size: $formattedSize MB, max apk size: $maxApkSizeMegabytes MB"

                    runBlocking {
                        telegramApi.sendMessage(
                            message = message,
                            token = token,
                            chatId = chatId,
                        )
                    }

                    throw GradleException(message)
                }

                println("Got file ${file.name} of $formattedSize MB, threshold is set to $maxApkSizeMegabytes MB")
            }
    }

    private fun getFileSizeMegabytes(file: File): Double = file.length().toDouble() / BYTES_IN_MEGABYTE

    companion object {
        const val APK_SIZE_FILE_NAME = "apk_size.txt"

        private const val DEFAULT_APK_SIZE_THRESHOLD = 10
        private const val BYTES_IN_MEGABYTE = 1_048_576
    }
}
