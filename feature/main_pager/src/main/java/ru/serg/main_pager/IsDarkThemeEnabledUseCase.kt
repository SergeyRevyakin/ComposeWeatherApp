package ru.serg.main_pager

import ru.serg.datastore.DataStoreDataSource
import javax.inject.Inject

class IsDarkThemeEnabledUseCase @Inject constructor(
    private val dataStoreDataSource: DataStoreDataSource
) {
    operator fun invoke() =
        dataStoreDataSource.isDarkThemeEnabled

}