plugins {
    id("android-dagger-convention")
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.voxeldev.todoapp.utils"

    defaultConfig {
        resValue("string", "todo_api_base_url", extra["TODO_API_BASE_URL"].toString())
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.ktor.client.core)
}
