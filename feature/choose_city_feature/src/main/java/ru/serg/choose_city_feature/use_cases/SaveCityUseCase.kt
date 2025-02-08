package ru.serg.choose_city_feature.use_cases

import ru.serg.local.LocalDataSource
import ru.serg.model.CityItem
import javax.inject.Inject

class SaveCityUseCase @Inject constructor(
    private val localDataSource: LocalDataSource,
) {
    suspend operator fun invoke(cityItem: CityItem) =
        localDataSource.insertCityItemToSearchHistory(cityItem)
}