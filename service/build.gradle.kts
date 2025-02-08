plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "ru.serg.service"
    compileSdk = 34

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