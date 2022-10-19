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

@OptIn(FlowPreview::class)
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

    var sharedFlow = MutableSharedFlow<String>(
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
            sharedFlow.debounce(3000).collectLatest {
                onTextChanged(it)
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

    private fun onTextChanged(input: String?) {
        viewModelScope.launch {
            remoteRepository.getCityForAutocomplete(input).collectLatest { networkResult ->
                when (networkResult) {
                    is NetworkResult.Loading -> {
                        screenState = screenState.copy(isLoading = true)
                    }
                    is NetworkResult.Error -> {
                        screenState = screenState.copy(
                            isLoading = false,
                            data = emptyList(),
                            message = (if (input.isNullOrBlank()) "Enter city name" else "Error")
                        )
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
                        screenState = if (cityList.isNullOrEmpty()) {
                            screenState.copy(
                                isLoading = false,
                                data = emptyList(),
                                message = "Nothing found"
                            )
                        } else {
                            screenState.copy(isLoading = false, data = cityList, message = null)
                        }
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
        screenState = if (input.isNotBlank()) {
            screenState.copy(isLoading = true)
        } else {
            screenState.copy(
                isLoading = false,
                data = emptyList(),
                message = "Enter city name"
            )
        }
        viewModelScope.launch {
            sharedFlow.emit(input)
        }
    }
}