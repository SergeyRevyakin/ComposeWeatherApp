package ru.serg.composeweatherapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ru.serg.composeweatherapp.data.data.CityItem
import ru.serg.composeweatherapp.data.data_source.LocalDataSource
import ru.serg.composeweatherapp.data.data_source.RemoteDataSource
import ru.serg.composeweatherapp.utils.NetworkResult
import javax.inject.Inject

class CitySearchUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {
    suspend fun fetchCityListFlow(input: String?): Flow<NetworkResult<List<CityItem>>> =
        remoteDataSource.getCityForAutocomplete(input).map { networkResult ->
            when (networkResult) {
                is NetworkResult.Loading -> NetworkResult.Loading()
                is NetworkResult.Error -> NetworkResult.Error(message = networkResult.message)
                is NetworkResult.Success -> {
                    val data = networkResult.data
                    val result = data?.map {
                        CityItem(
                            name = it.name.orEmpty(),
                            country = it.country.orEmpty(),
                            latitude = it.lat ?: 0.0,
                            longitude = it.lon ?: 0.0,
                            false
                        )
                    } ?: emptyList()
                    NetworkResult.Success(result)
                }
            }
        }

    fun getFavouriteCitiesFlow() =
        localDataSource.getCityHistorySearch().distinctUntilChanged()

    suspend fun saveCityItem(cityItem: CityItem) =
        localDataSource.insertCityItemToHistorySearch(cityItem)

    suspend fun deleteCityItem(cityItem: CityItem) =
        localDataSource.deleteCityItemToHistorySearch(cityItem)
}