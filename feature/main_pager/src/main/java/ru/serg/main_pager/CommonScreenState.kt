package ru.serg.main_pager

import ru.serg.model.UpdatedWeatherItem

sealed class CommonScreenState {
    object Empty : CommonScreenState()
    object Loading : CommonScreenState()
    data class Success(val updatedWeatherList: List<UpdatedWeatherItem>) : CommonScreenState()

    data class Updating(val updatedWeatherList: List<UpdatedWeatherItem>) : CommonScreenState()
    data class Error(val message: String? = null) : CommonScreenState()
}

sealed class WeatherState(val item: UpdatedWeatherItem) {
    class Loading(item: UpdatedWeatherItem) : WeatherState(item)

    class NoWeather(item: UpdatedWeatherItem) : WeatherState(item)

    class Success(item: UpdatedWeatherItem) : WeatherState(item)

    class Init(item: UpdatedWeatherItem) : WeatherState(item)


}