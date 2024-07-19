plugins {
    id("android-feature-convention")
}

android {
    namespace = "com.voxeldev.todoapp.designsystem"
}

dependencies {
    implementation(projects.source.core.utils)
}
