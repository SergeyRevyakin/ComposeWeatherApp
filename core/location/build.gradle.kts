plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin)
    id("kotlin-kapt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "ru.serg.location"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = "18"
    }
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.play.services.location)
    implementation(libs.androidx.annotation.jvm)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
}