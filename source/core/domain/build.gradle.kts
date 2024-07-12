plugins {
    id("android-dagger-convention")
}

android {
    namespace = "com.voxeldev.todoapp.domain"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)

    implementation(projects.source.core.api)
    implementation(projects.source.core.utils)
    implementation(projects.source.data.repository)
}
