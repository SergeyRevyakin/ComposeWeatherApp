package ru.serg.composeweatherapp.ui.screens.main_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.serg.composeweatherapp.data.data.CityItem
import ru.serg.composeweatherapp.data.data_source.LocalDataSource
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val localDataSource: LocalDataSource
) : ViewModel() {

    val citiesList = mutableStateOf(emptyList<CityItem?>())

    fun fillCitiesList(hasLocationPermission: Boolean) {
        viewModelScope.launch {
            localDataSource.getCityHistorySearchDao().collectLatest { items ->
                if (!hasLocationPermission) {
                    citiesList.value = items
                } else {
                    val resultList = listOf(null) + items
                    citiesList.value = resultList
                }
            }
        }
    }
}