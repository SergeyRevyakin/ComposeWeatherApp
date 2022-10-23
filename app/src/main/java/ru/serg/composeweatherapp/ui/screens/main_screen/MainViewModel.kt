package ru.serg.composeweatherapp.ui.screens.main_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.serg.composeweatherapp.data.LocalRepository
import ru.serg.composeweatherapp.data.data.CityItem
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val localRepository: LocalRepository
) : ViewModel() {

    val citiesList = mutableStateOf(emptyList<CityItem?>())

    init {
        fillCitiesList()
    }

    private fun fillCitiesList() {
        viewModelScope.launch {
            localRepository.getCityHistorySearchDao().collectLatest {
                val resultList = listOf(null) + it
                citiesList.value = resultList
            }
        }
    }
}