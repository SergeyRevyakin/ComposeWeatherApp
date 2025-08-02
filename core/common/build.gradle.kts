plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin)
}

android {
    namespace = "ru.serg.common"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }
}

dependencies {
    implementation(project(":res:strings"))
    implementation(project(":res:drawables"))

    implementation(libs.kotlinx.coroutines.android)

    testImplementation(project(":core:testing"))
}