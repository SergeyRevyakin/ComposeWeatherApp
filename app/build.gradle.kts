plugins {
    id("com.android.application")
    kotlin("android")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "ru.serg.composeweatherapp"
        minSdk = 27
        targetSdk = 33
        versionCode = 13
        versionName = "0.13"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        ksp {
            arg(RoomSchemaArgProvider(File(projectDir, "schemas")))
            arg("room.incremental", "true")
            arg("room.expandProjection", "true")
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
        kotlinCompilerExtensionVersion = "1.4.4"
    }

    packaging {
        resources {
            excludes.add("META-INF/*.version")
        }
    }

    namespace = "ru.serg.composeweatherapp"
}

dependencies {

    api(platform("androidx.compose:compose-bom:2023.04.00"))
    androidTestApi(platform("androidx.compose:compose-bom:2023.04.00"))

    implementation("androidx.core:core-ktx:1.10.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0-beta01")
    implementation("androidx.activity:activity-compose:1.7.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.4.1")
    debugImplementation("androidx.compose.ui:ui-tooling:1.4.1")

    //Compose
    implementation("androidx.compose.ui:ui:1.5.0-alpha02")
    implementation("androidx.compose.material:material:1.5.0-alpha02")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.1")
//    implementation ("androidx.compose.animation:animation:1.3.3"
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("androidx.compose.foundation:foundation")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    //Hilt
    implementation("com.google.dagger:hilt-android:2.45")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    kapt("com.google.dagger:hilt-android-compiler:2.45")
    implementation("androidx.hilt:hilt-work:1.0.0")
    kapt("androidx.hilt:hilt-compiler:1.0.0")

    //Ktor
    implementation("io.ktor:ktor-client-core:2.2.4")
    implementation("io.ktor:ktor-client-android:2.2.4")
    implementation("io.ktor:ktor-client-serialization:2.2.4")
    implementation("io.ktor:ktor-client-content-negotiation:2.2.4")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.2.4")
    implementation("io.ktor:ktor-client-logging:2.2.4")

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.31.0-alpha")
    implementation("com.google.accompanist:accompanist-permissions:0.31.0-alpha")
    implementation("com.google.accompanist:accompanist-swiperefresh:0.31.0-alpha")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.31.0-alpha")
    implementation("com.google.accompanist:accompanist-pager:0.31.0-alpha")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.31.0-alpha")

    implementation("com.google.android.gms:play-services-location:21.0.1")

    //Kotlin datetime
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

    //Room
    implementation("androidx.room:room-runtime:2.5.1")
    ksp("androidx.room:room-compiler:2.5.1")
    implementation("androidx.room:room-ktx:2.5.1")

    //WorkManager
    implementation("androidx.work:work-runtime-ktx:2.8.1")

    //Widgets
    implementation("androidx.glance:glance-appwidget:1.0.0-alpha05")

    //Data Store
    implementation("androidx.datastore:datastore-preferences:1.1.0-alpha04")

    //Permission as Flow
    implementation("dev.shreyaspatil.permission-flow:permission-flow-android:1.0.0")
    implementation("dev.shreyaspatil.permission-flow:permission-flow-compose:1.0.0")

    testImplementation("androidx.room:room-testing:2.5.1")

}
class RoomSchemaArgProvider(
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    val schemaDir: File
) : CommandLineArgumentProvider {

    override fun asArguments(): Iterable<String> {
        return listOf("room.schemaLocation=${schemaDir.path}")
    }
}