package ru.serg.composeweatherapp.ui.screens.main_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.serg.composeweatherapp.data.data.CityItem
import ru.serg.composeweatherapp.data.data_source.LocalDataSource
import ru.serg.composeweatherapp.data.data_source.LocationServiceImpl
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val locationService: LocationServiceImpl
) : ViewModel() {

    var citiesList: StateFlow<List<CityItem?>> = MutableStateFlow(emptyList())

    var isLoading = mutableStateOf(false)

    init {
        fillCitiesList(locationService.isLocationAvailable())
    }


    fun fillCitiesList(hasLocationPermission: Boolean) {
        viewModelScope.launch {
            isLoading.value = true
            citiesList = localDataSource.getCityHistorySearchDao().mapLatest { items ->
                isLoading.value = false

                if (!hasLocationPermission) {
                    items
                } else {
                    val resultList = listOf(null) + items
                    resultList
                }

            }.stateIn(
                scope = viewModelScope,
                initialValue = listOf(),
                started = SharingStarted.WhileSubscribed(5_000)
            )
        }

    }
}