plugins {
    id("android-dagger-convention")
}

android {
    namespace = "com.voxeldev.todoapp.database"

    defaultConfig {
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }
}

dependencies {
    api(libs.ktor.client.core)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    implementation(projects.source.core.api)
    implementation(projects.source.core.utils)
}
