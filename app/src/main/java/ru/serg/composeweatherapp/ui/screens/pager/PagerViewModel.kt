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
import ru.serg.composeweatherapp.data.PagerUseCase
import ru.serg.composeweatherapp.data.data.CityItem
import ru.serg.composeweatherapp.data.data.CoordinatesWrapper
import ru.serg.composeweatherapp.data.data.WeatherItem
import ru.serg.composeweatherapp.utils.DateUtils
import ru.serg.composeweatherapp.utils.NetworkResult
import javax.inject.Inject

@HiltViewModel
class PagerViewModel @Inject constructor(
    private val pagerUseCase: PagerUseCase,
    private val dateUtils: DateUtils
) : ViewModel() {

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    var localWeatherItem: MutableState<NetworkResult<WeatherItem>> =
        mutableStateOf(NetworkResult.Error(null))

    @SuppressLint("MissingPermission")
    fun initialize(city: CityItem? = null) {
        viewModelScope.launch {
            if (localWeatherItem.value is NetworkResult.Success) {
                checkLastUpdate(city)
            } else {
                if (localWeatherItem.value !is NetworkResult.Loading) {
                    fetchWeather(city)
                }
            }
        }
    }

    private suspend fun checkLastUpdate(city: CityItem?) {
        if (dateUtils.isFetchDateExpired(localWeatherItem.value.data?.lastUpdatedTime ?: 0L)) {
            fetchWeather(city)
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun fetchWeather(city: CityItem?) {
        if (city == null) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                viewModelScope.launch {
                    pagerUseCase.fetchCurrentLocationWeather(
                        CoordinatesWrapper(
                            it.latitude,
                            it.longitude
                        )
                    ).collect {
                        localWeatherItem.value = it
                    }
                }
            }
        } else {
            viewModelScope.launch {
                pagerUseCase.fetchCityWeather(city).collect {
                    localWeatherItem.value = it
                }
            }
        }

    }
}