plugins {
    id("android-dagger-convention")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.voxeldev.todoapp.local"
}

dependencies {
    implementation(libs.androidx.security.crypto)
    implementation(libs.kotlinx.serialization.json)

    implementation(projects.source.core.api)
    implementation(projects.source.core.utils)
}
