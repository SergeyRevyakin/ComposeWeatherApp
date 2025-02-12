import com.android.build.gradle.internal.api.BaseVariantOutputImpl

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.firebase)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.compose)
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "ru.serg.composeweatherapp"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 95
        versionName = "0.95"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        multiDexEnabled = true

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

    flavorDimensions += "environment"
//    productFlavors {
//        create("prod") {
//            dimension = "environment"
//            configure<CrashlyticsExtension> {
//                mappingFileUploadEnabled = true
//            }
//        }
//    }
//    packaging {
//        resources {
//            excludes.add("META-INF/**")
//            excludes.add("META-INF/versions/**")
//        }
//    }

    namespace = "ru.serg.composeweatherapp"

//    testOptions {
//        unitTests {
//            isIncludeAndroidResources = true
//            isReturnDefaultValues = true
//
//            all {
//                it.useJUnitPlatform()
//                it.testLogging {
//                    events = setOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
//                    showCauses = true
//                    showExceptions = true
//                }
//            }
//        }
//    }

//    androidComponents {
//        onVariants(selector().withBuildType("release")) {
//            // Exclude AndroidX version files
//            it.packaging.resources.excludes.add("META-INF/*.version")
//        }
//    }

//    testOptions {
//        unitTests {
//
//            testLogging {
//
////                outputs.upToDateWhen {false}
//                events = setOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
//                showCauses = true
//                showExceptions = true
//            }
//        }
//    }
}

dependencies {
    implementation(project(":core:database"))
    implementation(project(":core:model"))
    implementation(project(":core:network"))
    implementation(project(":core:network_visual_crossing_api"))
    implementation(project(":core:network_self_proxy"))
    implementation(project(":core:datastore"))
    implementation(project(":core:common"))
    implementation(project(":core:location"))
    implementation(project(":core:local"))
    implementation(project(":core:designsystem"))
    implementation(project(":feature:settings_feature"))
    implementation(project(":core:weather"))
    implementation(project(":core:navigation"))
    implementation(project(":work"))
    implementation(project(":feature:choose_city_feature"))
    implementation(project(":feature:city_weather"))
    implementation(project(":feature:main_pager"))
    implementation(project(":service"))
    implementation(project(":widgets"))
    implementation(project(":feature:widget_settings_feature"))


    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    implementation(libs.bundles.permission.flow)

    implementation(libs.bundles.hilt)
    ksp(libs.bundles.hilt.ksp)

    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.work.runtime.ktx)

    implementation(libs.androidx.datastore.preferences)

    implementation(libs.androidx.multidex)

    implementation(libs.androidx.glance)
    implementation(libs.androidx.glance.appwidget)
    implementation(libs.androidx.glance.material)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.kotlinx.serialization.json)
}