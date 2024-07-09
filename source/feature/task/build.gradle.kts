plugins {
    id("android-feature-convention")
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.voxeldev.todoapp.task"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(projects.source.core.api)
    implementation(projects.source.core.designsystem)
    implementation(projects.source.core.domain)
    implementation(projects.source.core.utils)
    implementation(projects.source.data.local)
    implementation(projects.source.data.network)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
