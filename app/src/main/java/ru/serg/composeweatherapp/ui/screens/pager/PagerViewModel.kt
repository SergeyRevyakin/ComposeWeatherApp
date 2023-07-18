@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package ru.serg.composeweatherapp.ui.screens.pager

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.util.date.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.serg.composeweatherapp.data.WeatherRepository
import ru.serg.composeweatherapp.data.data_source.LocationDataSource
import ru.serg.composeweatherapp.data.dto.CityItem
import ru.serg.composeweatherapp.ui.screens.ScreenState
import ru.serg.composeweatherapp.utils.DateUtils
import ru.serg.composeweatherapp.utils.NetworkResult
import javax.inject.Inject

@HiltViewModel
class PagerViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationService: LocationDataSource,
    private val dateUtils: DateUtils
) : ViewModel() {
//
//    var uiState: StateFlow<ScreenState> = MutableStateFlow(ScreenState.Empty)
//
//    private val _isRefreshing = MutableStateFlow(false)
//    val isRefreshing: StateFlow<Boolean> = _isRefreshing
//
//    @SuppressLint("MissingPermission")
//    fun initialize(city: CityItem? = null) {
//        viewModelScope.launch {
//            if (uiState.value is ScreenState.Empty) {
//                fetchWeather(city)
//            } else {
//                if (uiState.value is ScreenState.Success) {
//                    checkLastUpdate(city)
//                }
//            }
//        }
//    }
//
//    fun refresh(city: CityItem?) {
//        _isRefreshing.compareAndSet(expect = false, update = true)
//        viewModelScope.launch {
//            if (city == null) {
//                locationService.getLocationUpdate().flatMapLatest { coordinatesWrapper ->
//                    weatherRepository.fetchCurrentLocationWeather(
//                        coordinatesWrapper,
//                        true
//                    )
//                }.launchIn(this)
//            } else weatherRepository.fetchCityWeather(city, true).launchIn(this)
//        }
//    }
//
//    private suspend fun checkLastUpdate(city: CityItem?) {
//        if (dateUtils.isFetchDateExpired((uiState.last() as ScreenState.Success).weatherItem.lastUpdatedTime)) {
//            fetchWeather(city)
//        }
//    }
//
//    @SuppressLint("MissingPermission")
//    private fun fetchWeather(city: CityItem?) {
//        if (city == null) {
//            uiState =
//                locationService.getLocationUpdate().flatMapLatest { coordinatesWrapper ->
//                    weatherRepository.fetchCurrentLocationWeather(
//                        coordinatesWrapper
//                    ).map { networkResult ->
//                        when (networkResult) {
//                            is NetworkResult.Loading -> ScreenState.Loading
//                            is NetworkResult.Error -> ScreenState.Error(networkResult.message)
//                            is NetworkResult.Success -> networkResult.data?.let {
//                                _isRefreshing.compareAndSet(expect = true, update = false)
//                                ScreenState.Success(
//                                    it
//                                )
//                            } ?: ScreenState.Error("Can't recognise data")
//                        }
//                    }
//                }.stateIn(
//                    scope = viewModelScope,
//                    initialValue = ScreenState.Empty,
//                    started = SharingStarted.WhileSubscribed(5_000)
//                )
//        } else {
//            uiState = weatherRepository.fetchCityWeather(city).map { networkResult ->
//                when (networkResult) {
//                    is NetworkResult.Loading -> ScreenState.Loading
//                    is NetworkResult.Error -> ScreenState.Error(networkResult.message)
//                    is NetworkResult.Success -> networkResult.data?.let {
//                        _isRefreshing.compareAndSet(expect = true, update = false)
//                        ScreenState.Success(
//                            it
//                        )
//                    } ?: ScreenState.Error("Can't recognise data")
//                }
//            }.stateIn(
//                scope = viewModelScope,
//                initialValue = ScreenState.Empty,
//                started = SharingStarted.WhileSubscribed(5_000)
//            )
//        }
//    }
}