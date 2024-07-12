plugins {
    id("android-feature-convention")
}

android {
    namespace = "com.voxeldev.todoapp.auth"
}

dependencies {
    implementation(libs.yandex.authsdk)

    implementation(projects.source.core.api)
    implementation(projects.source.core.designsystem)
    implementation(projects.source.core.domain)
    implementation(projects.source.core.utils)
    implementation(projects.source.data.local)
}
