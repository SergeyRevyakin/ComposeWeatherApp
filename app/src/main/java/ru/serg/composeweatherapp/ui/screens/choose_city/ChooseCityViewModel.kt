package ru.serg.composeweatherapp.ui.screens.choose_city

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import ru.serg.composeweatherapp.data.LocalRepository
import ru.serg.composeweatherapp.data.RemoteRepository
import ru.serg.composeweatherapp.data.data.CityItem
import ru.serg.composeweatherapp.utils.NetworkResult
import javax.inject.Inject

@FlowPreview
@HiltViewModel
class ChooseCityViewModel @Inject constructor(
    val remoteRepository: RemoteRepository,
    val localRepository: LocalRepository
) : ViewModel() {

    var screenState by mutableStateOf(
        ChoseCityScreenStates(
            isLoading = false,
            message = "Enter city name"
        )
    )
        private set

    var searchHistoryItems by mutableStateOf(listOf<CityItem>())

    var inputSharedFlow = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        initDebounceSearcher()
        fillSearchHistory()
    }

    private fun initDebounceSearcher() {

        viewModelScope.launch {
            inputSharedFlow.debounce(3000).collectLatest {
                fetchCities(it)
            }
        }
    }

    private fun fillSearchHistory() {
        viewModelScope.launch {
            localRepository.getCityHistorySearchDao().collect {
                searchHistoryItems = it
            }
        }
    }

    private suspend fun fetchCities(input: String?) {
        remoteRepository.getCityForAutocomplete(input).collectLatest { networkResult ->
            when (networkResult) {
                is NetworkResult.Loading -> {
                    setLoadingState()
                }
                is NetworkResult.Error -> {
                    setErrorState(input)
                }
                is NetworkResult.Success -> {
                    val cityList = networkResult.data?.map {
                        CityItem(
                            name = it.name.orEmpty(),
                            country = it.country,
                            latitude = it.lat,
                            longitude = it.lon
                        )
                    }
                    if (cityList.isNullOrEmpty()) {
                        setErrorState(input)
                    } else {
                        screenState =
                            screenState.copy(isLoading = false, data = cityList, message = null)
                    }
                }
            }
        }
    }

    fun onCityClicked(cityItem: CityItem) {
        viewModelScope.launch {
            localRepository.insertCityItemToHistorySearch(cityItem)
        }
    }

    fun onDeleteClick(cityItem: CityItem) {
        viewModelScope.launch {
            localRepository.deleteCityItemToHistorySearch(cityItem)
        }
    }

    fun onSharedFlowText(input: String) {
        if (input.isNotBlank()) {
            setLoadingState()
        } else {
            setErrorState("")
        }
        viewModelScope.launch {
            inputSharedFlow.emit(input)
        }
    }

    private fun setErrorState(input: String?) {
        screenState = screenState.copy(
            isLoading = false,
            data = emptyList(),
            message = (if (input.isNullOrBlank()) "Enter city name" else "Error")
        )
    }

    private fun setLoadingState() {
        screenState = screenState.copy(isLoading = true)
    }
}