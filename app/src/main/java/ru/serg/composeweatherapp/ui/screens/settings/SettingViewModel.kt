package ru.serg.composeweatherapp.ui.screens.settings

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shreyaspatil.permissionFlow.PermissionFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import ru.serg.composeweatherapp.utils.Constants
import ru.serg.composeweatherapp.utils.WeatherAlarmManager
import ru.serg.composeweatherapp.utils.WorkerManager
import ru.serg.composeweatherapp.utils.isTiramisuOrAbove
import ru.serg.datastore.DataStoreDataSource
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val dataStoreDataSource: DataStoreDataSource,
    private val weatherAlarmManager: WeatherAlarmManager,
    private val workerManager: WorkerManager
) : ViewModel() {

    private val _isDarkModeEnabled = MutableStateFlow(false)
    val isDarkModeEnabled = _isDarkModeEnabled.asStateFlow()

    private var _isBackgroundFetchWeatherEnabled = MutableStateFlow(false)
    var isBackgroundFetchWeatherEnabled = _isBackgroundFetchWeatherEnabled.asStateFlow()

    private var _fetchFrequencyValue = MutableStateFlow(0f)
    var fetchFrequencyValue = _fetchFrequencyValue.asStateFlow()

    private var _isLocationEnabled = MutableStateFlow(false)
    var isLocationEnabled = _isLocationEnabled.asStateFlow()

    var measurementUnits = mutableIntStateOf(0)

    private var _alarmState = MutableStateFlow(false)
    var alarmState = _alarmState.asStateFlow()

    private var _isNotificationEnabled = MutableStateFlow(false)
    var isNotificationEnabled = _isNotificationEnabled.asStateFlow()

    private val fetchFrequency = dataStoreDataSource.fetchFrequencyInHours.distinctUntilChanged()

    init {
        initDarkModeChange()
        initBackgroundFetchWeatherChange()
        initFetchFrequencyValue()
        initLocation()
        initUnits()
        initAlarmState()
        if (isTiramisuOrAbove()) initNotification()
    }

    private fun initDarkModeChange() {
        viewModelScope.launch {
            dataStoreDataSource.isDarkThemeEnabled.collectLatest {
                _isDarkModeEnabled.value = it
            }
        }
    }

    private fun initBackgroundFetchWeatherChange() {
        _isBackgroundFetchWeatherEnabled.value = workerManager.isWorkerSet()
    }

    private fun initFetchFrequencyValue() {
        viewModelScope.launch {
            dataStoreDataSource.fetchFrequency.collect {
                _fetchFrequencyValue.value = it.toFloat()
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
                _isLocationEnabled.value = it.grantedPermissions.isNotEmpty()
            }
        }
    }

    private fun initUnits() {
        viewModelScope.launch {
            dataStoreDataSource.measurementUnits.collectLatest {
                measurementUnits.intValue = it
            }
        }
    }

    private fun initAlarmState() {
        weatherAlarmManager.isAlarmSet().let {
            _alarmState.value = it
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun initNotification() {
        val locationPermissionFlow = PermissionFlow.getInstance().getPermissionState(
            Manifest.permission.POST_NOTIFICATIONS,
        )

        viewModelScope.launch {
            locationPermissionFlow.collect {
                _isNotificationEnabled.value = it.isGranted
            }
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
                    initBackgroundFetchWeatherChange()
                }
            }
        } else {
            viewModelScope.launch {
                workerManager.disableWeatherWorker()
                initBackgroundFetchWeatherChange()
            }
        }
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