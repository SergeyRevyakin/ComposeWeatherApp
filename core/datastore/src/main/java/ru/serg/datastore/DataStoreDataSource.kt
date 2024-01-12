package ru.serg.datastore

import kotlinx.coroutines.flow.Flow

interface DataStoreDataSource {
    val isDarkThemeEnabled: Flow<Boolean>

    val measurementUnits: Flow<Int>

    val fetchFrequency: Flow<Int>

    val fetchFrequencyInHours: Flow<Int>

    val isUserNotificationOn: Flow<Boolean>

    val widgetColorCode: Flow<Long>

    val widgetBigFontSize: Flow<Int>

    val widgetSmallFontSize: Flow<Int>

    val widgetBottomPadding: Flow<Int>
    suspend fun saveDarkMode(isDark: Boolean)

    suspend fun saveFetchFrequency(positionInList: Int)

    suspend fun saveMeasurementUnits(enumPosition: Int)

    suspend fun saveUserNotification(isNotificationOn: Boolean)

    suspend fun saveWidgetColorCode(colorCode: Long)

    suspend fun saveWidgetBigFontSize(size: Int)

    suspend fun saveWidgetSmallFontSize(size: Int)

    suspend fun saveWidgetBottomPadding(padding: Int)

}