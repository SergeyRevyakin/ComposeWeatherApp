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

    init {
        initDarkModeChange()
        initBackgroundFetchWeatherChange()
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

}