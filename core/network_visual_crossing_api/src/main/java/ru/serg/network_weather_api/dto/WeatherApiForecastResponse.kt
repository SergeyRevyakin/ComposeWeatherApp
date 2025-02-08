package ru.serg.network_weather_api.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.serg.network_weather_api.dto.weather_api.Alerts
import ru.serg.network_weather_api.dto.weather_api.Current
import ru.serg.network_weather_api.dto.weather_api.Forecast
import ru.serg.network_weather_api.dto.weather_api.Location

@Serializable
data class WeatherApiForecastResponse(
    @SerialName("alerts")
    val alerts: Alerts? = Alerts(),
    @SerialName("current")
    val current: Current? = Current(),
    @SerialName("forecast")
    val forecast: Forecast? = Forecast(),
    @SerialName("location")
    val location: Location? = Location()
)