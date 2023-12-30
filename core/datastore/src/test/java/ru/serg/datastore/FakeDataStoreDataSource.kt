package ru.serg.datastore

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeDataStoreDataSource : DataStoreDataSource {
    override val isDarkThemeEnabled: Flow<Boolean> = flowOf(true)

    override val measurementUnits: Flow<Int> = flowOf(1)

    override val fetchFrequency: Flow<Int> = flowOf(1)

    override val fetchFrequencyInHours: Flow<Int> = flowOf(1)
    override val isUserNotificationOn: Flow<Boolean> = flowOf(true)
    override val widgetColorCode: Flow<Long> = flowOf(1L)

    override suspend fun saveDarkMode(isDark: Boolean) {

    }

    override suspend fun saveFetchFrequency(positionInList: Int) {

    }

    override suspend fun saveMeasurementUnits(enumPosition: Int) {

    }

    override suspend fun saveUserNotification(isNotificationOn: Boolean) {

    }

    override suspend fun saveWidgetColorCode(colorCode: Long) {

    }
}