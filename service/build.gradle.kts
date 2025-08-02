plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "ru.serg.service"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(project(":core:weather"))
    implementation(project(":core:location"))
    implementation(project(":core:datastore"))
    implementation(project(":core:notifications"))
    implementation(project(":res:drawables"))

    implementation(libs.androidx.appcompat)
    implementation(libs.bundles.hilt)

    ksp(libs.bundles.hilt.ksp)

}