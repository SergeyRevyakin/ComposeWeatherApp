package ru.serg.main_pager.use_case

import ru.serg.local.LocalDataSource
import javax.inject.Inject

class GetLocalStoredWeatherUseCase @Inject constructor(
    private val localDataSource: LocalDataSource
) {
    operator fun invoke() = localDataSource.getWeatherFlow()
}