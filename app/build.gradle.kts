import com.android.build.gradle.internal.api.BaseVariantOutputImpl

plugins {
    id("com.android.application")
    kotlin("android")
    id("dagger.hilt.android.plugin")
    id("kotlinx-serialization")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
}

android {
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.serg.composeweatherapp"
        minSdk = 27
        targetSdk = 34
        versionCode = 22
        versionName = "0.22"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    sourceSets {
        getByName("androidTest").assets.srcDir("$projectDir/schemas")
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    applicationVariants.all {
        outputs.forEach { output ->
            if (output is BaseVariantOutputImpl) {
                output.outputFileName =
                    "serg_weather_${this.versionCode}_${name}.${output.outputFile.extension}"
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = "18"
        freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.8"
    }

    packaging {
        resources {
            excludes.add("META-INF/*.version")
        }
    }

    namespace = "ru.serg.composeweatherapp"

//    androidComponents {
//        onVariants(selector().withBuildType("release")) {
//            // Exclude AndroidX version files
//            it.packaging.resources.excludes.add("META-INF/*.version")
//        }
//    }
}

dependencies {
    implementation(project(":core:database"))
    implementation(project(":core:model"))

    implementation(platform(libs.compose.bom))
    androidTestImplementation(platform(libs.compose.bom))

    implementation(libs.bundles.compose)

    implementation(libs.bundles.permission.flow)

    implementation(libs.bundles.ktor)

    implementation(libs.bundles.hilt)
    kapt(libs.bundles.hilt.kapt)

    implementation(libs.bundles.accompanist)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity.compose)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(libs.compose.ui.test.junit4)
//    implementation(libs.androidx.ui.tooling.preview)
//    debugImplementation(libs.androidx.ui.tooling)

    //Compose
//    implementation(libs.compose.ui)
//    implementation(libs.androidx.material)
//    implementation(libs.androidx.material3)
//    implementation(libs.androidx.material.icons.extended)
//    implementation(libs.androidx.navigation.compose)
    implementation(libs.foundation)

    // Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    //Hilt
//    implementation(libs.hilt.android)
//    implementation(libs.androidx.hilt.navigation.compose)
//    kapt(libs.hilt.android.compiler)

//
//    implementation(libs.accompanist.systemuicontroller)
//    implementation(libs.accompanist.permissions)
//    implementation(libs.accompanist.swiperefresh)
//    implementation(libs.accompanist.navigation.animation)
//    implementation(libs.accompanist.pager)
//    implementation(libs.accompanist.pager.indicators)

    implementation(libs.play.services.location)

    //Kotlin datetime
    implementation(libs.kotlinx.datetime)

//    //Room


    //WorkManager
    implementation(libs.androidx.work.runtime.ktx)

    //Widgets
    implementation(libs.androidx.glance.appwidget)

    //Data Store
    implementation(libs.androidx.datastore.preferences)


}