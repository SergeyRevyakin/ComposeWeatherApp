package ru.serg.composeweatherapp.ui.screens.city_weather_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.serg.composeweatherapp.data.PagerUseCase
import ru.serg.composeweatherapp.ui.screens.ScreenState
import ru.serg.composeweatherapp.utils.Constants
import ru.serg.composeweatherapp.utils.NetworkResult
import javax.inject.Inject

@HiltViewModel
class CityWeatherViewModel @Inject constructor(
    private val pagerUseCase: PagerUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var uiState: StateFlow<ScreenState> = MutableStateFlow(ScreenState.Empty)

    init {
        viewModelScope.launch {
            savedStateHandle.get<String>(Constants.CITY_ITEM)?.let {
                uiState =
                    pagerUseCase.fetchWeather(Json.decodeFromString(it)).map { networkResult ->
                        when (networkResult) {
                            is NetworkResult.Loading -> ScreenState.Loading
                            is NetworkResult.Error -> ScreenState.Error(null)
                            is NetworkResult.Success -> ScreenState.Success(networkResult.data)
                        }
                    }.stateIn(
                        scope = viewModelScope,
                        initialValue = ScreenState.Empty,
                        started = SharingStarted.WhileSubscribed(5_000)
                    )
            }
        }
    }
}