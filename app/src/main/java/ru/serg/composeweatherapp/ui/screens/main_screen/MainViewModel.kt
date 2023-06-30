package ru.serg.composeweatherapp.ui.screens.main_screen

import android.Manifest
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shreyaspatil.permissionFlow.PermissionFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.serg.composeweatherapp.data.data_source.LocalDataSource
import ru.serg.composeweatherapp.data.dto.CityItem
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val localDataSource: LocalDataSource
) : ViewModel() {

    var citiesList: StateFlow<List<CityItem?>> = MutableStateFlow(emptyList())

    var isLoading = mutableStateOf(false)

    init {
        val locationPermissionFlow = PermissionFlow.getInstance().getMultiplePermissionState(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )

        viewModelScope.launch {
            locationPermissionFlow.collect {
                fillCitiesList(it.grantedPermissions.isNotEmpty())
            }
        }
    }

    private fun fillCitiesList(hasLocationPermission: Boolean) {
        viewModelScope.launch {
            isLoading.value = true
            citiesList = localDataSource.getCityHistorySearch().mapLatest { items ->
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