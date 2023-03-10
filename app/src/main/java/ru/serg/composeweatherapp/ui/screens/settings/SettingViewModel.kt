package ru.serg.composeweatherapp.ui.screens.settings

import android.Manifest
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shreyaspatil.permissionFlow.PermissionFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import ru.serg.composeweatherapp.data.data_source.DataStoreDataSource
import ru.serg.composeweatherapp.utils.Constants
import ru.serg.composeweatherapp.utils.WeatherAlarmManager
import ru.serg.composeweatherapp.utils.WorkerManager
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val dataStoreDataSource: DataStoreDataSource,
    private val weatherAlarmManager: WeatherAlarmManager,
    private val workerManager: WorkerManager
) : ViewModel() {

    var isDarkModeEnabled = mutableStateOf(false)

    var isBackgroundFetchWeatherEnabled = mutableStateOf(false)

    var fetchFrequencyValue = mutableStateOf(0f)

    var isLocationEnabled = mutableStateOf(false)

    var measurementUnits = mutableStateOf(0)

    var alarmState = mutableStateOf(false)

    private val fetchFrequency = dataStoreDataSource.fetchFrequencyInHours.distinctUntilChanged()

    init {
        initDarkModeChange()
        initBackgroundFetchWeatherChange()
        initFetchFrequencyValue()
        initLocation()
        initUnits()
        initAlarmState()
    }

    private fun initDarkModeChange() {
        viewModelScope.launch {
            dataStoreDataSource.isDarkThemeEnabled.collectLatest {
                isDarkModeEnabled.value = it
            }
        }
    }

    private fun initBackgroundFetchWeatherChange() {
        isBackgroundFetchWeatherEnabled.value = workerManager.isWorkerSet()
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

    private fun initUnits() {
        viewModelScope.launch {
            dataStoreDataSource.measurementUnits.collectLatest {
                measurementUnits.value = it
            }
        }
    }

    private fun initAlarmState() {
        weatherAlarmManager.isAlarmSet().let {
            alarmState.value = it
        }
    }

    fun onScreenModeChanged(isDark: Boolean) {
        viewModelScope.launch {
            dataStoreDataSource.saveDarkMode(isDark)
        }
    }

    fun onBackgroundFetchChanged(isEnabled: Boolean) {
        if (isEnabled) {
            viewModelScope.launch {
                fetchFrequency.collectLatest {
                    workerManager.setWeatherWorker(it)
                }
            }
        } else {
            workerManager.disableWeatherWorker()
        }

        initBackgroundFetchWeatherChange()
    }

    fun onFrequencyChanged(positionInList: Int) {
        viewModelScope.launch {
            dataStoreDataSource.saveFetchFrequency(positionInList)
            workerManager.setWeatherWorker(Constants.HOUR_FREQUENCY_LIST[positionInList])
        }
    }

    fun onUnitsChanged(position: Int) {
        viewModelScope.launch {
            dataStoreDataSource.saveMeasurementUnits(position)
        }
    }

    fun onAlarmChanged() {
        weatherAlarmManager.setOrCancelAlarm()
        initAlarmState()
    }

}