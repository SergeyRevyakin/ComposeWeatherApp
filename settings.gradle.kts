pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven {
            setUrl("https://androidx.dev/storage/compose-compiler/repository/")
        }
    }
}
rootProject.name = "ComposeWeatherApp"
include(":app")
include(":core:database")
include(":core:model")
include(":core:testing")
include(":core:network")
include(":core:datastore")
include(":core:common")
include(":core:location")
include(":core:local")
include(":core:designsystem")
include(":core:weather")
include(":feature:settings_feature")
include(":work")
include(":feature:choose_city_feature")
include(":feature:city_weather")
include(":shared:weather_elements")
include(":feature:main_pager")
include(":core:notifications")
include(":service")
include(":res:strings")
include(":res:drawables")
include(":widgets")
include(":feature:widget_settings_feature")
include(":core:navigation")
include(":core:network_weather_api")
