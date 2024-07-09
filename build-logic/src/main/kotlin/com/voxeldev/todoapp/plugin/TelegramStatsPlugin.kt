package com.voxeldev.todoapp.plugin

import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.AndroidComponentsExtension
import com.voxeldev.todoapp.telegram.TelegramApi
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.create

/**
 * @author nvoxel
 */
class TelegramStatsPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val androidComponents = project.extensions.findByType(AndroidComponentsExtension::class.java)
            ?: throw GradleException("Android not found")

        val extension = project.extensions.create("telegramStats", TelegramStatsExtension::class)
        val telegramApi = TelegramApi(httpClient = HttpClient(OkHttp))
        androidComponents.onVariants { variant ->
            val artifacts = variant.artifacts.get(SingleArtifact.APK)
            project.tasks.register(
                "telegramStatsFor${variant.name.capitalized()}",
                TelegramStatsTask::class.java,
                telegramApi,
            ).configure {
                apkDir.set(artifacts)
                token.set(extension.token)
                chatId.set(extension.chatId)
            }
        }
    }
}

private fun String.capitalized(): String = "${this[0].uppercase()}${substring(1)}"

interface TelegramStatsExtension {
    val chatId: Property<String>
    val token: Property<String>
}
