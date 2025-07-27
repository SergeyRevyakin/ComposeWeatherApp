package ru.serg.city_weather.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.serg.common.NetworkResult
import ru.serg.common.asResult
import ru.serg.model.CityItem
import ru.serg.weather.WeatherRepository
import ru.serg.weather_elements.ScreenState
import javax.inject.Inject

@HiltViewModel
class CityWeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
) : ViewModel() {

    var uiState: MutableStateFlow<ScreenState> = MutableStateFlow(ScreenState.Loading)


    fun initScreen(city: CityItem) {
        viewModelScope.launch {
            weatherRepository.fetchCityWeatherFlow(city, false)
                .asResult()
                .collectLatest { networkResult ->
                    uiState.emit(
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
                    )
                }
//                }
        }
    }

    fun refresh() {
        viewModelScope.launch {

            if (uiState.value is ScreenState.Success) {
                weatherRepository.fetchCityWeatherFlow(
                    (uiState.value as ScreenState.Success).weatherItem.cityItem,
                    false
                )
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