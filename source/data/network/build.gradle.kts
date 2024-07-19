plugins {
    id("android-dagger-convention")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.voxeldev.todoapp.network"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.bundles.ktor)

    implementation(projects.source.core.api)
    implementation(projects.source.core.utils)
    implementation(projects.source.data.local)
}
