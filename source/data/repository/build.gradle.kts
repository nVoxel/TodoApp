plugins {
    id("android-dagger-convention")
    id("android-test-convention")
}

android {
    namespace = "com.voxeldev.todoapp.repository"
}

dependencies {
    implementation(projects.source.core.api)
    implementation(projects.source.core.utils)
    implementation(projects.source.data.database)
    implementation(projects.source.data.local)
    implementation(projects.source.data.network)
}
