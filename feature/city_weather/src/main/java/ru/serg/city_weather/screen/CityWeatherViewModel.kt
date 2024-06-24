package ru.serg.city_weather.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.serg.common.NetworkResult
import ru.serg.common.asResult
import ru.serg.navigation.CityWeatherScreen
import ru.serg.navigation.toCityItem
import ru.serg.weather.WeatherRepository
import ru.serg.weather_elements.ScreenState
import javax.inject.Inject

@HiltViewModel
class CityWeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    lateinit var uiState: StateFlow<ScreenState>

    init {
        initScreen()
    }

    private fun initScreen() {
        viewModelScope.launch {
            CityWeatherScreen.from(savedStateHandle)
                .let { city ->
                    uiState =
                        weatherRepository.getCityWeatherFlow(city.cityItem.toCityItem(), false)
                            .asResult()
                            .map { networkResult ->
                                when (networkResult) {
                                    is NetworkResult.Loading -> ScreenState.Loading
                                    is NetworkResult.Error -> ScreenState.Error(
                                        networkResult.message,
                                        networkResult.throwable
                                    )

                                    is NetworkResult.Success -> networkResult.data.let { weatherItem ->
                                        ScreenState.Success(
                                            weatherItem
                                        )
                                    }
                                }
                            }.stateIn(
                                scope = viewModelScope,
                                initialValue = ScreenState.Loading,
                                started = SharingStarted.WhileSubscribed(5_000)
                            )
                }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            CityWeatherScreen.from(savedStateHandle)
                .let { city ->
                    weatherRepository.getCityWeatherFlow(city.cityItem.toCityItem(), false)
                        .asResult()
                        .collectLatest { networkResult ->
                            when (networkResult) {
                                is NetworkResult.Loading -> ScreenState.Loading
                                is NetworkResult.Error -> ScreenState.Error(
                                    networkResult.message,
                                    networkResult.throwable
                                )

                                is NetworkResult.Success -> networkResult.data.let { weatherItem ->
                                    ScreenState.Success(
                                        weatherItem
                                    )
                                }
                            }
                        }
                }
        }
    }
}