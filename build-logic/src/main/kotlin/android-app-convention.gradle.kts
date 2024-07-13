import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.compose")
}

configure<BaseAppModuleExtension> {
    commonAndroid(libs = libs)

    signingConfigs {
        val localProperties = gradleLocalProperties(rootDir, providers)

        create("release") {
            storeFile = file(path = localProperties.getProperty("release.storeFile"))
            storePassword = localProperties.getProperty("release.storePassword")
            keyAlias = localProperties.getProperty("release.keyAlias")
            keyPassword = localProperties.getProperty("release.keyPassword")
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
        }
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.yandex.authsdk)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.startup)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.work.runtime.ktx)

    implementation(platform(libs.compose.bom))
    implementation(libs.material.icons.extended)
    implementation(libs.navigation.compose)

    implementation(libs.ktor.client.core)

    implementation(libs.dagger)
    ksp(libs.dagger.compiler)

    debugImplementation(libs.androidx.ui.tooling)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
