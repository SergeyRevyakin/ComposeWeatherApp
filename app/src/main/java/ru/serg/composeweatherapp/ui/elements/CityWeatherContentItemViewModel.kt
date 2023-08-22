package ru.serg.composeweatherapp.ui.elements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.serg.datastore.DataStoreDataSource
import ru.serg.model.enums.Units
import javax.inject.Inject

@HiltViewModel
class CityWeatherContentItemViewModel @Inject constructor(
    private val dataStoreDataSource: DataStoreDataSource
) : ViewModel() {

    lateinit var units: StateFlow<Units>

    init {
        viewModelScope.launch {
            units = dataStoreDataSource.measurementUnits.map {
                Units.values()[it]
            }.stateIn(
                scope = viewModelScope,
                initialValue = Units.METRIC,
                started = SharingStarted.WhileSubscribed(5_000)
            )
        }
    }
}