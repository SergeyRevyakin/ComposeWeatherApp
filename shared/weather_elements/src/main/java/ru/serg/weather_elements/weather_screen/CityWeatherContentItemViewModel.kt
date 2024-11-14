package ru.serg.weather_elements.weather_screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.serg.datastore.DataStoreDataSource
import ru.serg.model.enums.Units
import javax.inject.Inject

@HiltViewModel
class CityWeatherContentItemViewModel @Inject constructor(
    dataStoreDataSource: DataStoreDataSource
) : ViewModel() {

    private val units: StateFlow<Units> = dataStoreDataSource.measurementUnits.map {
        Units.entries[it]
    }.stateIn(
        scope = CoroutineScope(Dispatchers.IO),
        initialValue = Units.METRIC,
        started = SharingStarted.WhileSubscribed(5_000)
    )

    private val isAlertsEnabled: StateFlow<Boolean> =
        dataStoreDataSource.isWeatherAlertsEnabled.stateIn(
            scope = CoroutineScope(Dispatchers.IO),
            initialValue = false,
            started = SharingStarted.WhileSubscribed(5_000)
        )

    val screenState: StateFlow<CityWeatherContentScreenState> =
        combine(units, isAlertsEnabled) { unit: Units, isAlertsEnabled: Boolean ->
            CityWeatherContentScreenState(unit, isAlertsEnabled)
        }.stateIn(
            scope = CoroutineScope(Dispatchers.IO),
            initialValue = CityWeatherContentScreenState(Units.METRIC, true),
            started = SharingStarted.WhileSubscribed(5_000)
        )
}
