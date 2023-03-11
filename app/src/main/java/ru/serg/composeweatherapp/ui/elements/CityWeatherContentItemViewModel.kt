package ru.serg.composeweatherapp.ui.elements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.serg.composeweatherapp.data.data_source.DataStoreDataSource
import ru.serg.composeweatherapp.utils.Constants
import javax.inject.Inject

@HiltViewModel
class CityWeatherContentItemViewModel @Inject constructor(
    private val dataStoreDataSource: DataStoreDataSource
) : ViewModel() {

    var units: StateFlow<String> = MutableStateFlow(Constants.EMPTY_STRING)

    init {
        viewModelScope.launch {
            units = dataStoreDataSource.measurementUnits.map {
                Constants.DataStore.Units.values()[it].tempUnits
            }.stateIn(
                scope = viewModelScope,
                initialValue = Constants.EMPTY_STRING,
                started = SharingStarted.WhileSubscribed(5_000)
            )
        }
    }
}