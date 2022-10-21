package ru.serg.composeweatherapp.ui.screens.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.serg.composeweatherapp.data.DataStoreRepository
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    var isDarkModeEnabled = mutableStateOf(false)

    var isBackgroundFetchWeatherEnabled = mutableStateOf(false)

    var fetchFrequencyValue = mutableStateOf(0f)

    init {
        initDarkModeChange()
        initBackgroundFetchWeatherChange()
        initFetchFrequencyValue()
    }

    private fun initDarkModeChange() {
        viewModelScope.launch {
            dataStoreRepository.isDarkThemeEnabled.collectLatest {
                isDarkModeEnabled.value = it
            }
        }
    }

    private fun initBackgroundFetchWeatherChange() {
        viewModelScope.launch {
            dataStoreRepository.isBackgroundFetchWeatherEnabled.collectLatest {
                isBackgroundFetchWeatherEnabled.value = it
            }
        }
    }

    private fun initFetchFrequencyValue() {
        viewModelScope.launch {
            dataStoreRepository.fetchFrequency.collectLatest {
                fetchFrequencyValue.value = it.toFloat()
            }
        }
    }

    fun onScreenModeChanged(isDark: Boolean) {
        viewModelScope.launch {
            dataStoreRepository.saveDarkMode(isDark)
        }
    }

    fun onBackgroundFetchChanged(isEnabled: Boolean) {
        viewModelScope.launch {
            dataStoreRepository.saveBackgroundFetchWeatherEnabled(isEnabled)
        }
    }

    fun onFrequencyChanged(positionInList: Int) {
        viewModelScope.launch {
            dataStoreRepository.saveFetchFrequency(positionInList)
        }
    }

}