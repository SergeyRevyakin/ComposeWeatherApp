package ru.serg.composeweatherapp.ui.screens.settings

import android.Manifest
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shreyaspatil.permissionFlow.PermissionFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.serg.composeweatherapp.data.data_source.DataStoreDataSource
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val dataStoreDataSource: DataStoreDataSource,
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
        val locationPermissionFlow = PermissionFlow.getInstance().getMultiplePermissionState(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )

        viewModelScope.launch {
            locationPermissionFlow.collect {
                isLocationEnabled.value = it.grantedPermissions.isNotEmpty()
            }
        }
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