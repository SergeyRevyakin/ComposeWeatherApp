package ru.serg.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
object MainScreen

@Serializable
object SettingsScreen

@Serializable
object WidgetSettingsScreen

@Serializable
object CityFinderScreen

@Serializable
data class CityWeatherScreen(
    val cityItem: ParcCityItem
) {
    companion object {
        val typeMap = mapOf(typeOf<ParcCityItem>() to serializableType<ParcCityItem>())

        fun from(savedStateHandle: SavedStateHandle) =
            savedStateHandle.toRoute<CityWeatherScreen>(typeMap)
    }
}
