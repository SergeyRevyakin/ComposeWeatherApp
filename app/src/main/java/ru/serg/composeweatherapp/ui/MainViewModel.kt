package ru.serg.composeweatherapp.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.serg.composeweatherapp.data.remote.RemoteRepository
import ru.serg.composeweatherapp.data.remote.WeatherResult
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
) : ViewModel() {
    val weather = mutableStateOf<WeatherResult.Success?>(null)

    init {
        viewModelScope.launch {
            weather.value = remoteRepository.getWeather()
        }
    }
}