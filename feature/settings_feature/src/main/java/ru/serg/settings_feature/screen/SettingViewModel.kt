package ru.serg.settings_feature.screen

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
import ru.serg.datastore.DataStoreDataSource
import ru.serg.settings_feature.Constants
import ru.serg.settings_feature.isTiramisuOrAbove
import ru.serg.work.WorkerManager
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val dataStoreDataSource: DataStoreDataSource,
    private val workerManager: WorkerManager
) : ViewModel() {

    private val _isDarkModeEnabled = MutableStateFlow(false)
    val isDarkModeEnabled = _isDarkModeEnabled.asStateFlow()

    private var _isBackgroundFetchWeatherEnabled = MutableStateFlow(false)
    var isBackgroundFetchWeatherEnabled = _isBackgroundFetchWeatherEnabled.asStateFlow()

    private var _isAlertsEnabled = MutableStateFlow(false)
    var isAlertsEnabled = _isAlertsEnabled.asStateFlow()

    private var _fetchFrequencyValue = MutableStateFlow(0f)
    var fetchFrequencyValue = _fetchFrequencyValue.asStateFlow()

    private var _isLocationEnabled = MutableStateFlow(false)
    var isLocationEnabled = _isLocationEnabled.asStateFlow()

    var measurementUnits = mutableIntStateOf(0)

    private var _isNotificationEnabled = MutableStateFlow(false)
    var isNotificationEnabled = _isNotificationEnabled.asStateFlow()

    private var _isUserNotificationTurnOn = MutableStateFlow(false)
    var isUserNotificationTurnOn = _isUserNotificationTurnOn.asStateFlow()

    private val fetchFrequency = dataStoreDataSource.fetchFrequencyInHours.distinctUntilChanged()

    init {
        initDarkModeChange()
        initBackgroundFetchWeatherChange()
        initFetchFrequencyValue()
        initLocation()
        initUnits()
        initUserNotificationTurnOn()
        initWeatherAlertsChange()
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

    private fun initWeatherAlertsChange() {
        viewModelScope.launch {
            dataStoreDataSource.isWeatherAlertsEnabled.collectLatest {
                _isAlertsEnabled.value = it
            }
        }
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

    private fun initUserNotificationTurnOn() {
        viewModelScope.launch {
            dataStoreDataSource.isUserNotificationOn.collectLatest {
                _isUserNotificationTurnOn.value = it
            }
        }
    }

    fun saveUserNotifications(isNotificationsOn: Boolean) {
        viewModelScope.launch {
            dataStoreDataSource.saveUserNotification(isNotificationsOn)
        }
    }

    fun onAlertsChanged(isEnabled: Boolean) {
        viewModelScope.launch {
            dataStoreDataSource.saveWeatherAlertsEnabled(isEnabled)
        }
    }
}
