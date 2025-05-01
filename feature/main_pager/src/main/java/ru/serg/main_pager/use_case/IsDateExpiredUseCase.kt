package ru.serg.main_pager.use_case

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ru.serg.datastore.DataStoreDataSource
import ru.serg.main_pager.Constants
import javax.inject.Inject

class IsDateExpiredUseCase @Inject constructor(
    private val dataStoreDataSource: DataStoreDataSource
) {

    suspend operator fun invoke(timestamp: Long): Boolean {
        return dataStoreDataSource.fetchFrequency.map {
            (((Constants.HOUR_FREQUENCY_LIST[it]) * 60L * 60L * 1000L + timestamp) - System.currentTimeMillis()) < 0
        }.first()
    }
}