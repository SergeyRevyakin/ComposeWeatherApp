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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.serg.common.NetworkResult
import ru.serg.composeweatherapp.data.CitySearchUseCase
import ru.serg.model.CityItem
import javax.inject.Inject

@FlowPreview
@HiltViewModel
class ChooseCityViewModel @Inject constructor(
    private val citySearchUseCase: CitySearchUseCase,
) : ViewModel() {

    var screenState by mutableStateOf(
        ChoseCityScreenStates(
            isLoading = false,
            message = "Enter city name"
        )
    )
        private set

    var favouriteCitiesList: StateFlow<List<CityItem>> = MutableStateFlow(emptyList())

    private var _inputSharedFlow = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val inputSharedFlow = _inputSharedFlow.asSharedFlow()

    init {
        initFavouriteCities()
        initDebounceSearcher()
    }

    private fun initDebounceSearcher() {
        viewModelScope.launch {
            _inputSharedFlow.debounce(3000).collectLatest {
                if (it.isNotBlank()) fetchCities(it)
            }
        }
    }

    private fun initFavouriteCities() {
        favouriteCitiesList = citySearchUseCase.getFavouriteCitiesFlow()
            .stateIn(
                scope = viewModelScope,
                initialValue = emptyList(),
                started = SharingStarted.WhileSubscribed(5_000)
            )
    }

    private suspend fun fetchCities(input: String?) {
        citySearchUseCase.fetchCityListFlow(input).collectLatest { networkResult ->
            when (networkResult) {
                is NetworkResult.Loading -> {
                    setLoadingState()
                }

                is NetworkResult.Error -> {
                    setErrorState(networkResult.message)
                }

                is NetworkResult.Success -> {
                    val cityList = networkResult.data
                    if (cityList.isNullOrEmpty()) {
                        setErrorState(input, "No results found")
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
            citySearchUseCase.saveCityItem(cityItem)
        }
    }

    fun onDeleteClick(cityItem: CityItem) {
        viewModelScope.launch {
            citySearchUseCase.deleteCityItem(cityItem)
        }
    }

    fun onSharedFlowText(input: String) {
        if (input.isNotBlank()) {
            setLoadingState()
        } else {
            setErrorState("")
        }
        viewModelScope.launch {
            _inputSharedFlow.emit(input)
        }
    }

    private fun setErrorState(input: String?, errorText: String? = null) {
        screenState = screenState.copy(
            isLoading = false,
            data = emptyList(),
            message = (errorText ?: if (input.isNullOrBlank()) "Enter city name" else "Error")
        )
    }

    private fun setLoadingState() {
        screenState = screenState.copy(isLoading = true)
    }
}