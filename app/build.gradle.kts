import java.io.FileInputStream
import java.util.Properties

plugins {
    id("android-app-convention")
    id("build-verify")
}

android {
    namespace = "com.voxeldev.todoapp"

    defaultConfig {
        applicationId = "com.voxeldev.todoapp"
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = libs.versions.version.code.get().toInt()
        versionName = libs.versions.version.name.get()

        manifestPlaceholders["YANDEX_CLIENT_ID"] = extra["YANDEX_CLIENT_ID"].toString()
    }
}

val secrets = Properties()
secrets.load(FileInputStream(rootProject.file("secrets.properties")))
val telegramToken = secrets["telegramStats.token"].toString()
val telegramChatId = secrets["telegramStats.chatId"].toString()

telegramFile {
    sendFile = true
    token = telegramToken
    chatId = telegramChatId
}

validateApkSize {
    validationEnabled = true
    maxApkSizeMegabytes = 25
}

reportApkContents {
    reportEnabled = true
}

dependencies {
    implementation(projects.source.core.api)
    implementation(projects.source.core.designsystem)
    implementation(projects.source.core.domain)
    implementation(projects.source.core.utils)
    implementation(projects.source.data.database)
    implementation(projects.source.data.local)
    implementation(projects.source.data.network)
    implementation(projects.source.data.repository)
    implementation(projects.source.feature.about)
    implementation(projects.source.feature.auth)
    implementation(projects.source.feature.list)
    implementation(projects.source.feature.settings)
    implementation(projects.source.feature.task)
}
