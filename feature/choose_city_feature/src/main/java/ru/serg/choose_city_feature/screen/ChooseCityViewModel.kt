package ru.serg.choose_city_feature.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.serg.choose_city_feature.CitySearchUseCase
import ru.serg.choose_city_feature.screen.screen_state.Action
import ru.serg.choose_city_feature.screen.screen_state.ScreenError
import ru.serg.choose_city_feature.screen.screen_state.ScreenState
import ru.serg.common.NetworkResult
import ru.serg.common.asResult
import ru.serg.model.CityItem
import javax.inject.Inject

@FlowPreview
@HiltViewModel
class ChooseCityViewModel @Inject constructor(
    private val citySearchUseCase: CitySearchUseCase,
) : ViewModel() {

    private val _screenState: MutableStateFlow<ScreenState> =
        MutableStateFlow(ScreenState.getInitialState())
    val screenState = _screenState.asStateFlow()

    init {
        initDebounceSearcher()
        initFavouriteCities()
    }

    private fun initDebounceSearcher() {
        var lastSearchInput = _screenState.value.searchText
        viewModelScope.launch {
            screenState.debounce(3000).distinctUntilChanged().collectLatest { state ->
                state.searchText.takeIf {
                    state.searchText.isNotBlank() && state.searchText != lastSearchInput
                }?.let {
                    lastSearchInput = it
                    fetchCities(it)
                } ?: handleIntent(Intent.OnLoading(false))
            }
        }
    }

    private fun initFavouriteCities() {
        viewModelScope.launch {
            citySearchUseCase.getFavouriteCitiesFlow().collectLatest {
                handleIntent(Intent.FavouriteCityListChanged(it))
            }
        }
    }

    private suspend fun fetchCities(input: String?) {
        citySearchUseCase.fetchCityListFlow(input).asResult().collectLatest { networkResult ->
            when (networkResult) {
                is NetworkResult.Loading -> {
                    handleIntent(Intent.OnLoading(true))
                }

                is NetworkResult.Error -> {
                    handleIntent(Intent.OnNetworkError)
                }

                is NetworkResult.Success -> {
                    handleIntent(Intent.OnCityDataUpdated(networkResult.data))
                }
            }
        }
    }

    private fun onAddCityClick(cityItem: CityItem) {
        viewModelScope.launch {
            citySearchUseCase.saveCityItem(cityItem)
        }
    }

    private fun onDeleteCityClick(cityItem: CityItem) {
        viewModelScope.launch {
            citySearchUseCase.deleteCityItem(cityItem)
        }
    }

    fun doAction(action: Action) {
        when (action) {
            is Action.OnAddCityClick -> onAddCityClick(action.cityItem)
            is Action.OnDeleteCityClick -> onDeleteCityClick(action.cityItem)
            is Action.OnTextChanged -> handleIntent(Intent.OnTextChanges(action.inputText))
        }
    }

    fun handleIntent(intent: Intent) {
        _screenState.update {

            when (intent) {
                is Intent.OnTextChanges -> {
                    onTextEntered(intent.inputText)
                }

                is Intent.OnCityDataUpdated -> {
                    onCityDataUpdate(intent.list)
                }

                is Intent.OnNetworkError -> {
                    onNetworkError()
                }

                is Intent.FavouriteCityListChanged -> {
                    favouriteCityListChanged(intent.list)
                }

                is Intent.OnLoading -> {
                    setLoading(intent.isLoading)
                }
            }
        }
    }


    private fun onTextEntered(input: String): ScreenState {
        return screenState.value.copy(
            isLoading = input.isNotBlank(),
            screenError = null,
            searchText = input
        )
    }

    private fun onCityDataUpdate(list: List<CityItem>): ScreenState {
        return if (list.isEmpty()) {
            screenState.value.copy(
                isLoading = false,
                screenError = ScreenError.NO_CITIES,
                foundCitiesList = emptyList()
            )
        } else {
            screenState.value.copy(
                isLoading = false,
                screenError = null,
                foundCitiesList = list
            )
        }
    }

    private fun favouriteCityListChanged(list: List<CityItem>): ScreenState {
        return screenState.value.copy(
            favoriteCitiesList = list
        )
    }

    private fun onNetworkError(): ScreenState {
        return screenState.value.copy(
            isLoading = false,
            screenError = ScreenError.NETWORK_ERROR,
        )
    }

    private fun setLoading(isLoading: Boolean): ScreenState {
        return screenState.value.copy(
            isLoading = isLoading,
        )
    }
}