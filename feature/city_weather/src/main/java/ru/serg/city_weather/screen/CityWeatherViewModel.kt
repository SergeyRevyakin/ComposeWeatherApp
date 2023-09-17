@file:OptIn(FlowPreview::class)

package ru.serg.city_weather.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serg.weather.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import ru.serg.city_weather.Constants
import ru.serg.common.NetworkResult
import ru.serg.common.asResult
import ru.serg.weather_elements.ScreenState
import javax.inject.Inject

@HiltViewModel
class CityWeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    lateinit var uiState: StateFlow<ScreenState>

    init {
        viewModelScope.launch {
            savedStateHandle.get<String>(Constants.CITY_ITEM)?.let { city ->
                uiState =
                    weatherRepository.getCityWeatherFlow(Json.decodeFromString(city), false)
                        .asResult()
                        .map { networkResult ->
                            when (networkResult) {
                                is NetworkResult.Loading -> ScreenState.Loading
                                is NetworkResult.Error -> ScreenState.Error(networkResult.message)
                                is NetworkResult.Success -> networkResult.data.let { weatherItem ->
                                    ScreenState.Success(
                                        weatherItem
                                    )
                                }
                            }
                        }.debounce(300).stateIn(
                        scope = viewModelScope,
                        initialValue = ScreenState.Empty,
                        started = SharingStarted.WhileSubscribed(5_000)
                    )
            }
        }
    }
}