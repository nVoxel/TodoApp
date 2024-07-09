plugins {
    `kotlin-dsl`
    id("java-gradle-plugin")
}

gradlePlugin {
    plugins.register("telegram-stats") {
        id = name
        implementationClass = "com.voxeldev.todoapp.plugin.TelegramStatsPlugin"
    }
}

dependencies {
    implementation(libs.agp)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.kotlin.ksp.gradle)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)

    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
