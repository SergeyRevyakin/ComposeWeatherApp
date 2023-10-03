package ru.serg.weather_elements.elements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.serg.datastore.DataStoreDataSource
import ru.serg.model.enums.Units
import javax.inject.Inject

@HiltViewModel
class CityWeatherContentItemViewModel @Inject constructor(
    dataStoreDataSource: DataStoreDataSource
) : ViewModel() {

    val units: StateFlow<Units> = dataStoreDataSource.measurementUnits.map {
        Units.entries[it]
    }.stateIn(
        scope = viewModelScope,
        initialValue = Units.METRIC,
        started = SharingStarted.WhileSubscribed(5_000)
    )
}