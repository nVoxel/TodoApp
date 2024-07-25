package com.voxeldev.todoapp.plugin.report

import com.voxeldev.todoapp.telegram.TelegramApi
import com.voxeldev.todoapp.utils.APK_SUFFIX
import com.voxeldev.todoapp.utils.extensions.convertSizeBytes
import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.util.zip.ZipFile
import javax.inject.Inject

/**
 * @author nvoxel
 */
internal abstract class ReportApkContentsTask @Inject constructor(
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
    abstract val reportEnabled: Property<Boolean>

    @TaskAction
    fun reportApkContents() {
        val apkDirFile = apkDir.get().asFile

        val reportEnabled = reportEnabled.getOrElse(true)

        if (!reportEnabled) return

        val token = token.get()
        val chatId = chatId.get()

        apkDirFile.listFiles()
            ?.filter { file -> file.name.endsWith(APK_SUFFIX) }
            ?.forEach { file ->
                val report = generateReport(file = file)

                runBlocking {
                    telegramApi.sendMessage(
                        message = report,
                        token = token,
                        chatId = chatId,
                        useMarkdownV2 = true,
                    )
                }
            }
    }

    private fun generateReport(file: File): String {
        val sizeMap = hashMapOf<String, Long>()

        ZipFile(file).use { zip ->
            zip.entries().asSequence().forEach { zipEntry ->
                val root = zipEntry.name.takeWhile { char -> char != File.separatorChar }

                if (sizeMap.containsKey(root)) {
                    sizeMap[root] = sizeMap[root]!! + zipEntry.size
                } else {
                    sizeMap[root] = zipEntry.size
                }
            }
        }

        val stringBuilder = StringBuilder(REPORT_HEADER)
        sizeMap.entries
            .sortedBy { entry -> entry.key }
            .forEach { entry ->
                stringBuilder.appendLine("- ${entry.key} ${entry.value.convertSizeBytes()}")
            }
        stringBuilder.append(REPORT_FOOTER)
        return stringBuilder.toString()
    }

    private companion object {
        const val REPORT_HEADER = "```APK\n"
        const val REPORT_FOOTER = "\n```"
    }
}
