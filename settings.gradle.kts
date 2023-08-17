pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        // There's no need to apply the Android plugin to buildSrc/build.gradle.kts,
        // so don't add the google() repo
        // (it won't hurt, but it's just not necessary)
        google()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
rootProject.name = "ComposeWeatherApp"
include(":app")
include(":core:database")
include(":core:model")
include(":core:testing")
include(":core:dto")
include(":core:network")
include(":core:datastore")
include(":core:common")
include(":core:location")
include(":core:local")
