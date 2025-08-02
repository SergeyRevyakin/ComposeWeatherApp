import com.android.build.gradle.BaseExtension
import org.jetbrains.kotlin.gradle.dsl.JvmDefaultMode
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.secrets) apply false
    alias(libs.plugins.firebase) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.parcelize) apply false
    alias(libs.plugins.room) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}

subprojects {
    plugins.withId("com.android.application") {
        extensions.configure<BaseExtension> {
            compileOptions {
                sourceCompatibility = JavaVersion.toVersion(libs.versions.javaVersion.get())
                targetCompatibility = JavaVersion.toVersion(libs.versions.javaVersion.get())
            }
        }
    }
    plugins.withId("com.android.library") {
        extensions.configure<BaseExtension> {
            compileOptions {
                sourceCompatibility = JavaVersion.toVersion(libs.versions.javaVersion.get())
                targetCompatibility = JavaVersion.toVersion(libs.versions.javaVersion.get())
            }
        }
    }
    plugins.withId("org.jetbrains.kotlin.android") {
        tasks.withType<KotlinJvmCompile>().configureEach {
            compilerOptions {
                jvmTarget = JvmTarget.fromTarget(libs.versions.javaVersion.get())
                optIn.add("kotlin.RequiresOptIn")
                freeCompilerArgs.addAll(listOf("-Xcontext-receivers", "-Xinline-classes"))
                progressiveMode.set(true)
                jvmDefault.set(JvmDefaultMode.NO_COMPATIBILITY)
            }
            // Adjust validation mode if Gradle 8+ and AGP < 8.1
            jvmTargetValidationMode.set(org.jetbrains.kotlin.gradle.dsl.jvm.JvmTargetValidationMode.WARNING)
        }
    }
}

