plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "ru.serg.city_weather"
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

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

dependencies {
    implementation(project(":core:weather"))
    implementation(project(":shared:weather_elements"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":res:strings"))

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    implementation(libs.bundles.hilt)
    ksp(libs.bundles.hilt.ksp)
    implementation(libs.kotlinx.serialization.json)
}