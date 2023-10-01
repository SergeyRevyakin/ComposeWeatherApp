package ru.serg.datastore

import kotlinx.coroutines.flow.Flow

interface DataStoreDataSource {
    val isDarkThemeEnabled: Flow<Boolean>

    val measurementUnits: Flow<Int>

    val fetchFrequency: Flow<Int>

    val fetchFrequencyInHours: Flow<Int>

    suspend fun saveDarkMode(isDark: Boolean)

    suspend fun saveFetchFrequency(positionInList: Int)

    suspend fun saveMeasurementUnits(enumPosition: Int)

}