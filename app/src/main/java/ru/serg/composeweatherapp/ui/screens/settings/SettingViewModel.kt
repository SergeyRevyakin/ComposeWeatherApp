package ru.serg.composeweatherapp.ui.screens.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.serg.composeweatherapp.data.data_source.DataStoreDataSource
//import ru.serg.composeweatherapp.data.data_source.LocationServiceImpl
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val dataStoreDataSource: DataStoreDataSource,
//    private val locationServiceImpl: LocationServiceImpl
) : ViewModel() {

    var isDarkModeEnabled = mutableStateOf(false)

    var isBackgroundFetchWeatherEnabled = mutableStateOf(false)

    var fetchFrequencyValue = mutableStateOf(0f)

    var isLocationEnabled = mutableStateOf(false)

    init {
        initDarkModeChange()
        initBackgroundFetchWeatherChange()
        initFetchFrequencyValue()
        initLocation()
    }

    private fun initDarkModeChange() {
        viewModelScope.launch {
            dataStoreDataSource.isDarkThemeEnabled.collectLatest {
                isDarkModeEnabled.value = it
            }
        }
    }

    private fun initBackgroundFetchWeatherChange() {
        viewModelScope.launch {
            dataStoreDataSource.isBackgroundFetchWeatherEnabled.collectLatest {
                isBackgroundFetchWeatherEnabled.value = it
            }
        }
    }

    private fun initFetchFrequencyValue() {
        viewModelScope.launch {
            dataStoreDataSource.fetchFrequency.collectLatest {
                fetchFrequencyValue.value = it.toFloat()
            }
        }
    }

    private fun initLocation() {
        isLocationEnabled.value = true//locationServiceImpl.isLocationAvailable()
    }

    fun onScreenModeChanged(isDark: Boolean) {
        viewModelScope.launch {
            dataStoreDataSource.saveDarkMode(isDark)
        }
    }

    fun onBackgroundFetchChanged(isEnabled: Boolean) {
        viewModelScope.launch {
            dataStoreDataSource.saveBackgroundFetchWeatherEnabled(isEnabled)
        }
    }

    fun onFrequencyChanged(positionInList: Int) {
        viewModelScope.launch {
            dataStoreDataSource.saveFetchFrequency(positionInList)
        }
    }

}