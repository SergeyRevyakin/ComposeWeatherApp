plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.serialization)
//    id("org.jetbrains.kotlin.plugin.serialization")
    id("kotlin-kapt")
    alias(libs.plugins.hilt)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
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
}

dependencies {
    implementation(project(":core:datastore"))
    implementation(project(":core:common"))

    implementation(libs.bundles.ktor)
    implementation(libs.kotlinx.serialization.json)


    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)


}