package ru.serg.composeweatherapp.ui.screens.pager

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.util.date.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.serg.composeweatherapp.data.WeatherRepository
import ru.serg.composeweatherapp.data.data.CityItem
import ru.serg.composeweatherapp.data.data.CoordinatesWrapper
import ru.serg.composeweatherapp.data.data.WeatherItem
import ru.serg.composeweatherapp.ui.screens.ScreenState
import ru.serg.composeweatherapp.utils.DateUtils
import ru.serg.composeweatherapp.utils.NetworkResult
import javax.inject.Inject

@HiltViewModel
class PagerViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val dateUtils: DateUtils
) : ViewModel() {

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    var localWeatherItem: MutableStateFlow<NetworkResult<WeatherItem>> =
        MutableStateFlow(NetworkResult.Error(null))

    var uiState: StateFlow<ScreenState> = MutableStateFlow(ScreenState.Empty)

    @SuppressLint("MissingPermission")
    fun initialize(city: CityItem? = null) {
        viewModelScope.launch {
            if (uiState.value is ScreenState.Empty) {
                fetchWeather(city)
            } else {
                if (uiState.value is ScreenState.Success<*>) {
                    checkLastUpdate(city)
                }
            }
        }
    }

    private suspend fun checkLastUpdate(city: CityItem?) {
//        if (dateUtils.isFetchDateExpired(localWeatherItem.value.data?.lastUpdatedTime ?: 0L)) {
            fetchWeather(city)
//        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun fetchWeather(city: CityItem?) {
        if (city == null) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                viewModelScope.launch {
                    uiState = weatherRepository.fetchCurrentLocationWeather(
                        CoordinatesWrapper(
                            it.latitude,
                            it.longitude
                        )
                    ).map { networkResult ->
                        when (networkResult) {
                            is NetworkResult.Loading -> ScreenState.Loading
                            is NetworkResult.Error -> ScreenState.Error(networkResult.message)
                            is NetworkResult.Success -> ScreenState.Success(networkResult.data)
                        }
                    }.stateIn(
                        scope = viewModelScope,
                        initialValue = ScreenState.Empty,
                        started = SharingStarted.WhileSubscribed(5_000)
                    )
                }
            }
        } else {
            viewModelScope.launch {
                uiState = weatherRepository.fetchCityWeather(city).map { networkResult ->
                    when (networkResult) {
                        is NetworkResult.Loading -> ScreenState.Loading
                        is NetworkResult.Error -> ScreenState.Error(networkResult.message)
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