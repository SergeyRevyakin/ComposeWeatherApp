plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlinx-serialization")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "ru.serg.model"
    compileSdk = 34

    defaultConfig {
        minSdk = 27
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
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
}