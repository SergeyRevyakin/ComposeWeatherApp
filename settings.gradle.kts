dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "ComposeWeatherApp"
include(":app")
include(":core:database")
include(":core:model")
include(":core:testing")
