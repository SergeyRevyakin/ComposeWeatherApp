buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.gradle.build)
//        classpath(libs.kotlin.gradle.plugin)
//        classpath(libs.hilt.android.gradle.plugin)
//        classpath(libs.kotlin.serialization)
        classpath(libs.secrets.gradle.plugin)
    }
}

plugins {
//    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
//    alias(libs.plugins.secrets) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

