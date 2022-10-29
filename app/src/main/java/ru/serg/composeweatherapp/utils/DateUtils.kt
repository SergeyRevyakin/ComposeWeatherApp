package ru.serg.composeweatherapp.utils

import io.ktor.util.date.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ru.serg.composeweatherapp.data.DataStoreRepository
import javax.inject.Inject

class DateUtils @Inject constructor(
    val dataStoreRepository: DataStoreRepository
) {
    //    init {
//        val refreshTime = dataStoreRepository.fetchFrequency {
//
//        }.flowOn(Dispatchers.Default)
//    }
//
    suspend fun isFetchDateExpired(timestamp: Long): Boolean {
        return dataStoreRepository.fetchFrequency.map {
            (((Constants.HOUR_FREQUENCY_LIST[it]) * 60L * 60L * 1000L + timestamp) - getTimeMillis()) < 0
        }.first()

    }
}