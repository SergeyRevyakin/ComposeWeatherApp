package ru.serg.main_pager.use_case

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ru.serg.datastore.DataStoreDataSource
import ru.serg.main_pager.Constants
import javax.inject.Inject

class IsDateExpiredUseCase @Inject constructor(
    private val dataStoreDataSource: DataStoreDataSource
) {

    suspend operator fun invoke(timestamp: Long, isFavorite: Boolean = false): Boolean {
        return if (isFavorite) isExpired(timestamp, 0.25)
        else dataStoreDataSource.fetchFrequency.map {
            isExpired(timestamp, Constants.HOUR_FREQUENCY_LIST[it].toDouble())
        }.first()
    }

    private fun isExpired(
        timestamp: Long,
        frequency: Double
    ): Boolean {
        return (frequency * 60L * 60L * 1000L + timestamp) - System.currentTimeMillis() < 0
    }
}