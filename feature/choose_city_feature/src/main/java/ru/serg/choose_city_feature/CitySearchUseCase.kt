package ru.serg.choose_city_feature

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.serg.local.LocalDataSource
import ru.serg.model.CityItem
import ru.serg.network.RemoteDataSource
import javax.inject.Inject

class CitySearchUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {
    fun fetchCityListFlow(input: String?): Flow<List<CityItem>> =
        remoteDataSource.getCityForAutocomplete(input).map { items ->
            items.map {
                CityItem(
                    name = it.name.orEmpty(),
                    country = it.country.orEmpty(),
                    latitude = it.lat ?: 0.0,
                    longitude = it.lon ?: 0.0,
                    false
                )
            }
        }

    fun getFavouriteCitiesFlow() =
        localDataSource.getCitySearchHistory()

    suspend fun saveCityItem(cityItem: CityItem) =
        localDataSource.insertCityItemToSearchHistory(cityItem)

    suspend fun deleteCityItem(cityItem: CityItem) =
        localDataSource.deleteCityItemHistorySearch(cityItem)
}