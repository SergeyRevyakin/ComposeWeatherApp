package ru.serg.composeweatherapp.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.serg.composeweatherapp.data.LocalRepository
import ru.serg.composeweatherapp.data.RemoteRepository
import ru.serg.composeweatherapp.data.remote.responses.OneCallResponse
import ru.serg.composeweatherapp.data.remote.responses.WeatherResponse
import ru.serg.composeweatherapp.data.room.WeatherUnit
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

    val screenState = mutableStateOf(ScreenState.LOADING)

    var counter = 0

    @SuppressLint("MissingPermission")
    fun initialize() {

        snapshotFlow {
            flowOf(oneCallWeather.value, simpleWeather.value)
        }.onEach {
            it.collectLatest {
                when {
                    it is NetworkResult.Success -> {
                        setSuccess()
                    }
                    it is NetworkResult.Error -> {
                        setError()
                    }

                }
            }
        }.launchIn(viewModelScope)

        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            if (it != null) {
                println()
                viewModelScope.launch {
                    localRepository.saveCurrentLocation(it.latitude, it.longitude)
                }

                viewModelScope.launch {
                    remoteRepository.getWeatherW(it.latitude, it.longitude).collectLatest {
                        simpleWeather.value = it
                    }
                }
                viewModelScope.launch {
                    remoteRepository.getWeather(it.latitude, it.longitude).collectLatest {
                        oneCallWeather.value = it
                    }
                }
            } else {

                viewModelScope.launch {
                    localRepository.getLastSavedLocation().let {
                        viewModelScope.launch {
                            remoteRepository.getWeatherW(it.latitude, it.longitude).collectLatest {
                                simpleWeather.value = it
                            }
                        }
                        viewModelScope.launch {
                            remoteRepository.getWeather(it.latitude, it.longitude).collectLatest {
                                oneCallWeather.value = it
                            }
                        }
                    }
                }

            }

        }.addOnFailureListener {
            viewModelScope.launch {
                localRepository.getLastSavedLocation().let {
                    viewModelScope.launch {
                        remoteRepository.getWeatherW(it.latitude, it.longitude).collectLatest {
                            simpleWeather.value = it
                        }
                    }
                    viewModelScope.launch {
                        remoteRepository.getWeather(it.latitude, it.longitude).collectLatest {
                            oneCallWeather.value = it
                        }
                    }
                }
            }
        }
    }


    private fun setSuccess() {
//        if (isLoadingCompleted.value) return

        if (simpleWeather.value is NetworkResult.Success && oneCallWeather.value is NetworkResult.Success) {
            screenState.value = ScreenState.DATA
        }
    }

    private fun setError() {
        screenState.value = ScreenState.ERROR
    }
}