package ru.serg.composeweatherapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.serg.common.NetworkResult
import ru.serg.local.LocalDataSource
import ru.serg.model.CityItem
import ru.serg.network.RemoteDataSource
import javax.inject.Inject

class CitySearchUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {
    fun fetchCityListFlow(input: String?): Flow<NetworkResult<List<CityItem>>> =
        remoteDataSource.getCityForAutocomplete(input).map { networkResult ->
            when (networkResult) {
                is NetworkResult.Loading -> NetworkResult.Loading
                is NetworkResult.Error -> NetworkResult.Error(message = networkResult.message)
                is NetworkResult.Success -> {
                    val data = networkResult.data
                    val result = data.map {
                        CityItem(
                            name = it.name.orEmpty(),
                            country = it.country.orEmpty(),
                            latitude = it.lat ?: 0.0,
                            longitude = it.lon ?: 0.0,
                            false
                        )
                    }
                    NetworkResult.Success(result)
                }
            }
        }

    fun getFavouriteCitiesFlow() =
        localDataSource.getCitySearchHistory()

    suspend fun saveCityItem(cityItem: CityItem) =
        localDataSource.insertCityItemToSearchHistory(cityItem)

    suspend fun deleteCityItem(cityItem: CityItem) =
        localDataSource.deleteCityItemToHistorySearch(cityItem)
}