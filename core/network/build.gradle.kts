plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.secrets)
}

android {
    namespace = "ru.serg.network"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = "18"
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core:datastore"))
    implementation(project(":core:common"))
    implementation(project(":core:model"))

    implementation(libs.bundles.ktor)
    implementation(libs.kotlinx.serialization.json)


    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    testImplementation(libs.ktor.test)
    testImplementation(project(":core:testing"))
}