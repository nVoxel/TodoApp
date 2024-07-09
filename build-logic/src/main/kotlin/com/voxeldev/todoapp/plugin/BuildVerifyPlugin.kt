package com.voxeldev.todoapp.plugin

import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.Variant
import com.android.build.gradle.internal.tasks.factory.dependsOn
import com.voxeldev.todoapp.plugin.size.ValidateApkSizeExtension
import com.voxeldev.todoapp.plugin.size.ValidateApkSizeTask
import com.voxeldev.todoapp.plugin.stats.TelegramStatsExtension
import com.voxeldev.todoapp.plugin.stats.TelegramStatsTask
import com.voxeldev.todoapp.telegram.TelegramApi
import com.voxeldev.todoapp.utils.extensions.capitalized
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
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

        val validateApkSizeExtension = project.extensions.create("validateApkSize", ValidateApkSizeExtension::class)
        val telegramStatsExtension = project.extensions.create("telegramStats", TelegramStatsExtension::class)

        val telegramApi = TelegramApi(httpClient = HttpClient(OkHttp))

        androidComponents.onVariants { variant ->
            val artifacts = variant.artifacts.get(SingleArtifact.APK)

            val validateTask = registerValidateTask(
                project = project,
                variant = variant,
                telegramApi = telegramApi,
            ).apply {
                configure {
                    apkDir.set(artifacts)
                    token.set(telegramStatsExtension.token)
                    chatId.set(telegramStatsExtension.chatId)
                    validationEnabled.set(validateApkSizeExtension.validationEnabled)
                    maxApkSizeMegabytes.set(validateApkSizeExtension.maxApkSizeMegabytes)
                }
            }

            val statsTask = registerStatsTask(
                project = project,
                variant = variant,
                telegramApi = telegramApi,
            ).apply {
                configure {
                    apkDir.set(artifacts)
                    token.set(telegramStatsExtension.token)
                    chatId.set(telegramStatsExtension.chatId)
                }
            }

            statsTask.dependsOn(validateTask)
        }
    }

    private fun registerValidateTask(
        project: Project,
        variant: Variant,
        telegramApi: TelegramApi,
    ): TaskProvider<ValidateApkSizeTask> = project.tasks.register(
        "validateApkSizeFor${variant.name.capitalized()}",
        ValidateApkSizeTask::class.java,
        telegramApi,
    )

    private fun registerStatsTask(
        project: Project,
        variant: Variant,
        telegramApi: TelegramApi,
    ): TaskProvider<TelegramStatsTask> = project.tasks.register(
        "telegramStatsFor${variant.name.capitalized()}",
        TelegramStatsTask::class.java,
        telegramApi,
    )
}
