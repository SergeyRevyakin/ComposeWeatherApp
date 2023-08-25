package ru.serg.main_pager

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ru.serg.datastore.DataStoreDataSource
import javax.inject.Inject

class DateUseCase @Inject constructor(
    private val dataStoreDataSource: DataStoreDataSource
) {

    suspend fun isFetchDateExpired(timestamp: Long): Boolean {
        return dataStoreDataSource.fetchFrequency.map {
            (((Constants.HOUR_FREQUENCY_LIST[it]) * 60L * 60L * 1000L + timestamp) - System.currentTimeMillis()) < 0
        }.first()

    }

}