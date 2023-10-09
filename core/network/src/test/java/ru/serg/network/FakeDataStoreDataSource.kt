package ru.serg.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.serg.datastore.DataStoreDataSource

class FakeDataStoreDataSource : DataStoreDataSource {
    override val isDarkThemeEnabled: Flow<Boolean> = flowOf(true)

    override val measurementUnits: Flow<Int> = flowOf(1)

    override val fetchFrequency: Flow<Int> = flowOf(1)

    override val fetchFrequencyInHours: Flow<Int> = flowOf(1)

    override suspend fun saveDarkMode(isDark: Boolean) {

    }

    override suspend fun saveFetchFrequency(positionInList: Int) {

    }

    override suspend fun saveMeasurementUnits(enumPosition: Int) {

    }
}