package ru.serg.composeweatherapp.ui.screens.pager

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.util.date.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.serg.composeweatherapp.data.WeatherRepository
import ru.serg.composeweatherapp.data.data.CityItem
import ru.serg.composeweatherapp.ui.screens.ScreenState
import ru.serg.composeweatherapp.utils.DateUtils
import ru.serg.composeweatherapp.utils.Ext.locationFlow
import ru.serg.composeweatherapp.utils.NetworkResult
import javax.inject.Inject

@HiltViewModel
class PagerViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val dateUtils: DateUtils
) : ViewModel() {

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    var uiState: StateFlow<ScreenState> = MutableStateFlow(ScreenState.Empty)

    @SuppressLint("MissingPermission")
    fun initialize(city: CityItem? = null) {
        viewModelScope.launch {
            if (uiState.value is ScreenState.Empty) {
                fetchWeather(city)
            } else {
                if (uiState.value is ScreenState.Success) {
                    checkLastUpdate(city)
                }
            }
        }
    }

    private suspend fun checkLastUpdate(city: CityItem?) {
        if (dateUtils.isFetchDateExpired((uiState.value as ScreenState.Success).weatherItem.lastUpdatedTime)) {
            fetchWeather(city)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @SuppressLint("MissingPermission")
    private suspend fun fetchWeather(city: CityItem?) {
        if (city == null) {
            uiState =
                fusedLocationProviderClient.locationFlow().flatMapLatest { coordinatesWrapper ->
                    weatherRepository.fetchCurrentLocationWeather(
                        coordinatesWrapper
                    ).map { networkResult ->
                        when (networkResult) {
                            is NetworkResult.Loading -> ScreenState.Loading
                            is NetworkResult.Error -> ScreenState.Error(networkResult.message)
                            is NetworkResult.Success -> networkResult.data?.let {
                                ScreenState.Success(
                                    it
                                )
                            } ?: ScreenState.Error("Can't recognise data")
                        }
                    }
                }.stateIn(
                    scope = viewModelScope,
                    initialValue = ScreenState.Empty,
                    started = SharingStarted.WhileSubscribed(5_000)
                )
        } else {
            uiState = weatherRepository.fetchCityWeather(city).map { networkResult ->
                when (networkResult) {
                    is NetworkResult.Loading -> ScreenState.Loading
                    is NetworkResult.Error -> ScreenState.Error(networkResult.message)
                    is NetworkResult.Success -> networkResult.data?.let {
                        ScreenState.Success(
                            it
                        )
                    } ?: ScreenState.Error("Can't recognise data")
                }
            }.stateIn(
                scope = viewModelScope,
                initialValue = ScreenState.Empty,
                started = SharingStarted.WhileSubscribed(5_000)
            )
        }
    }
}