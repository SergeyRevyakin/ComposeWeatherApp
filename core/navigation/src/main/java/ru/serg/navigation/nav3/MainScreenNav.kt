package ru.serg.navigation.nav3

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import ru.serg.model.CityItem

@Serializable
data object MainScreenNav : NavKey

@Serializable
data object SearchScreenNav : NavKey

@Serializable
data object SettingsScreenNav : NavKey

@Serializable
data class CityWeatherScreenNav(val cityItem: CityItem) : NavKey

@Serializable
data object WidgetSettingsScreenNav : NavKey
