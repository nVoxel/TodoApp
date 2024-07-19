plugins {
    id("android-default-convention")
}

android {
    namespace = "com.voxeldev.todoapp.api"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}
