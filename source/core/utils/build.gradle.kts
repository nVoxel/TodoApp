plugins {
    id("android-dagger-convention")
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.voxeldev.todoapp.utils"

    defaultConfig {
        resValue("string", "version_name", libs.versions.version.name.get().toString())
        resValue("string", "todo_api_base_url", extra["TODO_API_BASE_URL"].toString())
        resValue("string", "github_profile_url", extra["GITHUB_PROFILE_URL"].toString())
        resValue("string", "github_repo_url", extra["GITHUB_REPO_URL"].toString())
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.ktor.client.core)
}
