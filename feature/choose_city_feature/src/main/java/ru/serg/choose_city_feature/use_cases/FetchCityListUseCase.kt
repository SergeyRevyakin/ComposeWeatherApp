package ru.serg.choose_city_feature.use_cases

import com.serg.network_self_proxy.SelfProxyRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.serg.model.CityItem
import javax.inject.Inject

class FetchCityListUseCase @Inject constructor(
    private val selfProxyRemoteDataSource: SelfProxyRemoteDataSource,
) {
    operator fun invoke(input: String?): Flow<List<CityItem>> =
        selfProxyRemoteDataSource.getCitiesForSearch(input).map { items ->
            items.map {
                CityItem(
                    name = it.name,
                    country = it.county,
                    latitude = it.latitude,
                    longitude = it.longitude,
                    secondsOffset = it.secondsOffset ?: 0L,
                    false
                )
            }
        }
}