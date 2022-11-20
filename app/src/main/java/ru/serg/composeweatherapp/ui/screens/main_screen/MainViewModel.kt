package ru.serg.composeweatherapp.ui.screens.main_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
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

    val citiesList = mutableStateOf(emptyList<CityItem?>())

    var isLoading = mutableStateOf(false)

    init {
        fillCitiesList(locationService.isLocationAvailable())
    }


    fun fillCitiesList(hasLocationPermission: Boolean) {
        viewModelScope.launch {
            isLoading.value = true
            localDataSource.getCityHistorySearchDao().collectLatest { items ->
                if (!hasLocationPermission) {
                    citiesList.value = items
                    isLoading.value = false
                } else {
                    val resultList = listOf(null) + items
                    citiesList.value = resultList
                    isLoading.value = false
                }
            }
        }
    }
}