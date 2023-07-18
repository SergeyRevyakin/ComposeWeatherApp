buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:8.2.0-alpha12")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.22")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.44.2")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.8.22")
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
    }
}

plugins {
    id("com.google.devtools.ksp") version "1.8.22-1.0.11" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}