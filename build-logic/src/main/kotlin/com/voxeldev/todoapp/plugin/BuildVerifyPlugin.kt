package com.voxeldev.todoapp.plugin

import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.Variant
import com.android.build.gradle.internal.tasks.factory.dependsOn
import com.voxeldev.todoapp.plugin.report.ReportApkContentsExtension
import com.voxeldev.todoapp.plugin.report.ReportApkContentsTask
import com.voxeldev.todoapp.plugin.size.ValidateApkSizeExtension
import com.voxeldev.todoapp.plugin.size.ValidateApkSizeTask
import com.voxeldev.todoapp.plugin.stats.TelegramFileExtension
import com.voxeldev.todoapp.plugin.stats.TelegramFileTask
import com.voxeldev.todoapp.telegram.TelegramApi
import com.voxeldev.todoapp.utils.extensions.capitalized
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import libs
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.create

/**
 * @author nvoxel
 */
class BuildVerifyPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val androidComponents = project.extensions.findByType(AndroidComponentsExtension::class.java)
            ?: throw GradleException("Android not found")

        val reportApkContentsExtension = project.extensions.create(
            "reportApkContents",
            ReportApkContentsExtension::class,
        )
        val validateApkSizeExtension = project.extensions.create("validateApkSize", ValidateApkSizeExtension::class)
        val telegramFileExtension = project.extensions.create("telegramFile", TelegramFileExtension::class)

        val telegramApi = TelegramApi(httpClient = HttpClient(OkHttp))

        androidComponents.onVariants { variant ->
            val artifacts = variant.artifacts.get(SingleArtifact.APK)

            val reportApkContentsTask = registerReportApkContentsTask(
                project = project,
                variant = variant,
                telegramApi = telegramApi,
            ).apply {
                configure {
                    apkDir.set(artifacts)
                    token.set(telegramFileExtension.token)
                    chatId.set(telegramFileExtension.chatId)
                    reportEnabled.set(reportApkContentsExtension.reportEnabled)
                }
            }

            val validateApkSizeTask = registerValidateApkSizeTask(
                project = project,
                variant = variant,
                telegramApi = telegramApi,
            ).apply {
                configure {
                    apkDir.set(artifacts)
                    token.set(telegramFileExtension.token)
                    chatId.set(telegramFileExtension.chatId)
                    validationEnabled.set(validateApkSizeExtension.validationEnabled)
                    maxApkSizeMegabytes.set(validateApkSizeExtension.maxApkSizeMegabytes)
                }
            }

            val fileTask = registerFileTask(
                project = project,
                variant = variant,
                telegramApi = telegramApi,
                buildVariant = variant.name,
                versionCode = project.libs.versions.version.code.get().toString(),
            ).apply {
                configure {
                    apkDir.set(artifacts)
                    sendFile.set(telegramFileExtension.sendFile)
                    token.set(telegramFileExtension.token)
                    chatId.set(telegramFileExtension.chatId)
                }
            }

            fileTask.dependsOn(validateApkSizeTask)
            reportApkContentsTask.dependsOn(fileTask)
        }
    }

    private fun registerReportApkContentsTask(
        project: Project,
        variant: Variant,
        telegramApi: TelegramApi,
    ): TaskProvider<ReportApkContentsTask> = project.tasks.register(
        "reportApkContentsFor${variant.name.capitalized()}",
        ReportApkContentsTask::class.java,
        telegramApi,
    )

    private fun registerValidateApkSizeTask(
        project: Project,
        variant: Variant,
        telegramApi: TelegramApi,
    ): TaskProvider<ValidateApkSizeTask> = project.tasks.register(
        "validateApkSizeFor${variant.name.capitalized()}",
        ValidateApkSizeTask::class.java,
        telegramApi,
    )

    private fun registerFileTask(
        project: Project,
        variant: Variant,
        telegramApi: TelegramApi,
        buildVariant: String,
        versionCode: String,
    ): TaskProvider<TelegramFileTask> = project.tasks.register(
        "telegramFileFor${variant.name.capitalized()}",
        TelegramFileTask::class.java,
        telegramApi,
        buildVariant,
        versionCode,
    )

    companion object {
        const val BUILD_VERIFY_TAG = "BuildVerify"

        const val APK_SIZE_FILE_NAME = "apk_size.txt"
    }
}
