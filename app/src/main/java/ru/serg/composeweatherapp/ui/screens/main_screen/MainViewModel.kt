package ru.serg.composeweatherapp.ui.screens.main_screen

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.serg.composeweatherapp.data.LocalRepository
import ru.serg.composeweatherapp.data.RemoteRepository
import ru.serg.composeweatherapp.data.remote.responses.OneCallResponse
import ru.serg.composeweatherapp.data.remote.responses.WeatherResponse
import ru.serg.composeweatherapp.utils.NetworkResult
import ru.serg.composeweatherapp.utils.ScreenState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) : ViewModel() {

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    val oneCallWeather = mutableStateOf<NetworkResult<OneCallResponse>>(NetworkResult.Loading())
    val simpleWeather = mutableStateOf<NetworkResult<WeatherResponse>>(NetworkResult.Loading())

    var screenState by mutableStateOf(ScreenState.LOADING)

    @SuppressLint("MissingPermission")
    fun initialize() {
        screenState = ScreenState.LOADING
        snapshotFlow {
            flowOf(oneCallWeather.value, simpleWeather.value)
        }.onEach { flow ->
            flow.collectLatest {
                when (it) {
                    is NetworkResult.Success -> {
                        setSuccess()
                    }
                    is NetworkResult.Error -> {
                        setError()
                    }
                    else -> {}
                }
            }
        }.launchIn(viewModelScope)

        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                viewModelScope.launch {
                    localRepository.saveCurrentLocation(location.latitude, location.longitude)
                    fetchWeather(location.latitude, location.longitude)
                }
            } else {
                getLastKnownLocationAndFetchWeather()
            }
        }.addOnFailureListener {
            getLastKnownLocationAndFetchWeather()
        }
    }

    private suspend fun fetchWeather(latitude: Double, longitude: Double) {
        remoteRepository.getWeatherW(latitude, longitude).collectLatest {
            simpleWeather.value = it
        }
        remoteRepository.getWeather(latitude, longitude).collectLatest {
            oneCallWeather.value = it
        }
    }

    private fun getLastKnownLocationAndFetchWeather() {
        viewModelScope.launch {
            localRepository.getLastSavedLocation().let {
                fetchWeather(it.latitude, it.longitude)
            }
        }
    }

    private fun setSuccess() {
        if (simpleWeather.value is NetworkResult.Success && oneCallWeather.value is NetworkResult.Success) {
            viewModelScope.launch {
                delay(600)
                screenState = ScreenState.DATA
            }
        }
    }

    private fun setError() {
        screenState = ScreenState.ERROR
    }
}