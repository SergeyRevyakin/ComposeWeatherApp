package ru.serg.composeweatherapp.ui.screens.choose_city

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.serg.composeweatherapp.data.RemoteRepository
import ru.serg.composeweatherapp.data.data.CityItem
import ru.serg.composeweatherapp.utils.NetworkResult
import javax.inject.Inject

@HiltViewModel
class ChooseCityViewModel @Inject constructor(
    val remoteRepository: RemoteRepository
) : ViewModel() {

    var screenState by mutableStateOf(ChoseCityScreenStates(isLoading = false))
        private set

    fun onTextChanged(input: String?) {
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
                            message = "Error"
                        )
                    }
                    is NetworkResult.Success -> {
                        val cityList = networkResult.data?.map {
                            CityItem(
                                name = it.name,
                                country = it.country,
                                latitude = it.lat,
                                longitude = it.lon
                            )
                        }
                        screenState = if (cityList.isNullOrEmpty()){
                            screenState.copy(isLoading = false, data = emptyList(), message = "Nothing found")
                        } else {
                            screenState.copy(isLoading = false, data = cityList, message = null)
                        }
                    }
                }
            }
        }
    }
}