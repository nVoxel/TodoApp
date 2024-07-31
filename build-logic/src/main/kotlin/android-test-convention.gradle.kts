plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

dependencies {
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.ktor.client.mock)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
}
