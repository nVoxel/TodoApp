plugins {
    id("android-feature-convention")
}

android {
    namespace = "com.voxeldev.todoapp.settings"
}

dependencies {
    implementation(libs.androidx.work.runtime.ktx)

    implementation(projects.source.core.api)
    implementation(projects.source.core.designsystem)
    implementation(projects.source.core.domain)
    implementation(projects.source.core.utils)
    implementation(projects.source.data.local)
}
