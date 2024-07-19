package com.voxeldev.todoapp.plugin.size

import com.voxeldev.todoapp.plugin.BuildVerifyPlugin.Companion.APK_SIZE_FILE_NAME
import com.voxeldev.todoapp.plugin.BuildVerifyPlugin.Companion.BUILD_VERIFY_TAG
import com.voxeldev.todoapp.telegram.TelegramApi
import com.voxeldev.todoapp.utils.APK_SUFFIX
import com.voxeldev.todoapp.utils.DOUBLE_FORMAT
import com.voxeldev.todoapp.utils.extensions.bytesToMegabytes
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

        val validationEnabled = validationEnabled.getOrElse(DEFAULT_VALIDATION_ENABLED)
        val maxApkSizeMegabytes = maxApkSizeMegabytes.getOrElse(DEFAULT_APK_SIZE_THRESHOLD)

        if (!validationEnabled || maxApkSizeMegabytes < 1) return

        val token = token.get()
        val chatId = chatId.get()

        apkDirFile.listFiles()
            ?.filter { file -> file.name.endsWith(APK_SUFFIX) }
            ?.forEach { file ->
                val fileSizeMegabytes = file.length().bytesToMegabytes()
                val formattedSize = String.format(Locale.getDefault(), DOUBLE_FORMAT, fileSizeMegabytes)

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

                    throw GradleException("[$BUILD_VERIFY_TAG] $message")
                }

                println(
                    "[$BUILD_VERIFY_TAG] Got file ${file.name} of $formattedSize MB, threshold is set to $maxApkSizeMegabytes MB",
                )
            }
    }

    private companion object {
        const val DEFAULT_VALIDATION_ENABLED = true
        const val DEFAULT_APK_SIZE_THRESHOLD = 10
    }
}
