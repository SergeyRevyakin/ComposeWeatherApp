package ru.serg.datastore

import android.content.Context
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    context: Context
) : DataStoreDataSource {
    companion object {
        val IS_DARK_THEME = booleanPreferencesKey(Constants.DataStore.IS_DARK_THEME)
        val FETCH_FREQUENCY = intPreferencesKey(Constants.DataStore.FETCH_FREQUENCY)
        val MEASUREMENT_UNITS = intPreferencesKey(Constants.DataStore.MEASUREMENT_UNITS)
        val IS_USER_NOTIFICATIONS_ON =
            booleanPreferencesKey(Constants.DataStore.IS_USER_NOTIFICATIONS_ON)

        //Widget settings
        val WIDGET_COLOR = longPreferencesKey(Constants.DataStore.WIDGET_COLOR_CODE)
    }


    //DarkMode flag
    override val isDarkThemeEnabled: Flow<Boolean> = dataStore.data.map {
        it[IS_DARK_THEME]
            ?: (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES)
    }.distinctUntilChanged()

    override suspend fun saveDarkMode(isDark: Boolean) {
        dataStore.edit {
            it[IS_DARK_THEME] = isDark
        }
    }

    //Fetch frequency for background worker
    override val fetchFrequency: Flow<Int> = dataStore.data.map {
        it[FETCH_FREQUENCY] ?: 2
    }.distinctUntilChanged()

    override val fetchFrequencyInHours: Flow<Int> = dataStore.data.map {
        val positionInList = it[FETCH_FREQUENCY] ?: 2
        Constants.HOUR_FREQUENCY_LIST[positionInList]
    }

    override suspend fun saveFetchFrequency(positionInList: Int) {
        dataStore.edit {
            it[FETCH_FREQUENCY] = positionInList
        }
    }

    //Units of measurement with enum
    override val measurementUnits: Flow<Int> = dataStore.data.map {
        it[MEASUREMENT_UNITS] ?: 0
    }.distinctUntilChanged()

    override suspend fun saveMeasurementUnits(enumPosition: Int) {
        dataStore.edit {
            it[MEASUREMENT_UNITS] = enumPosition
        }
    }

    //Shows if user turned on notification
    override val isUserNotificationOn: Flow<Boolean> = dataStore.data.map {
        it[IS_USER_NOTIFICATIONS_ON] ?: false
    }.distinctUntilChanged()

    override suspend fun saveUserNotification(isNotificationOn: Boolean) {
        dataStore.edit {
            it[IS_USER_NOTIFICATIONS_ON] = isNotificationOn
        }
    }

    //Widget color
    override val widgetColorCode: Flow<Long> = dataStore.data.map {
        it[WIDGET_COLOR] ?: Constants.WHITE_COLOR_CODE
    }.distinctUntilChanged()

    override suspend fun saveWidgetColorCode(colorCode: Long) {
        dataStore.edit {
            it[WIDGET_COLOR] = colorCode
        }
    }
}