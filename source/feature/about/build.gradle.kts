plugins {
    id("android-feature-convention")
}

android {
    namespace = "com.voxeldev.todoapp.about"
}

dependencies {
    implementation(libs.bundles.div)

    implementation(projects.source.core.api)
    implementation(projects.source.core.designsystem)
    implementation(projects.source.core.utils)
}
