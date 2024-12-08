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
    override val widgetBigFontSize: Flow<Int> = flowOf(1)
    override val widgetSmallFontSize: Flow<Int> = flowOf(1)
    override val widgetBottomPadding: Flow<Int> = flowOf(1)
    override val isWidgetSystemDataShown: Flow<Boolean> = flowOf(true)
    override val widgetIconSize: Flow<Int> = flowOf(1)
    override val isWidgetWeatherChangesShown: Flow<Boolean> = flowOf(true)
    override val isWeatherAlertsEnabled: Flow<Boolean> = flowOf(true)

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

    override suspend fun saveWidgetBigFontSize(size: Int) {

    }

    override suspend fun saveWidgetSmallFontSize(size: Int) {

    }

    override suspend fun saveWidgetBottomPadding(padding: Int) {

    }

    override suspend fun saveWidgetSystemDataShown(isShown: Boolean) {

    }

    override suspend fun saveWidgetIconSize(iconSize: Int) {

    }

    override suspend fun saveWidgetWeatherChangesShown(isShown: Boolean) {

    }

    override suspend fun saveWeatherAlertsEnabled(isEnabled: Boolean) {

    }
}