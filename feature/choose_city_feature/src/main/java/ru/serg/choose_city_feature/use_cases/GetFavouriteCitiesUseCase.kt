package ru.serg.choose_city_feature.use_cases

import ru.serg.local.LocalDataSource
import javax.inject.Inject

class GetFavouriteCitiesUseCase @Inject constructor(
    private val localDataSource: LocalDataSource,
) {
    operator fun invoke() =
        localDataSource.getCitySearchHistory()
}