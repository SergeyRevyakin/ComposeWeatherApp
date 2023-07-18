package ru.serg.composeweatherapp.ui.screens.city_weather_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.serg.composeweatherapp.data.WeatherRepository
import ru.serg.composeweatherapp.ui.screens.ScreenState
import ru.serg.composeweatherapp.utils.Constants
import ru.serg.composeweatherapp.utils.NetworkResult
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
                    weatherRepository.getCityWeatherNoSavingFlow(Json.decodeFromString(city))
                        .map { networkResult ->
                            when (networkResult) {
                                is NetworkResult.Loading -> ScreenState.Loading
                                is NetworkResult.Error -> ScreenState.Error(networkResult.message)
                                is NetworkResult.Success -> networkResult.data?.let { weatherItem ->
                                    ScreenState.Success(
                                        weatherItem
                                    )
                                } ?: ScreenState.Error("Can't recognise data")
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