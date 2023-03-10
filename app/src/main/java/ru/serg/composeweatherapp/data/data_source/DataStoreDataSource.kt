package ru.serg.composeweatherapp.data.data_source

import android.content.Context
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.serg.composeweatherapp.utils.Constants
import ru.serg.composeweatherapp.utils.dataStore

class DataStoreDataSource(context: Context) {
    companion object {
        val IS_DARK_THEME = booleanPreferencesKey(Constants.DataStore.IS_DARK_THEME)
        val FETCH_FREQUENCY = intPreferencesKey(Constants.DataStore.FETCH_FREQUENCY)
        val MEASUREMENT_UNITS = intPreferencesKey(Constants.DataStore.MEASUREMENT_UNITS)
    }

    private val dataStore = context.dataStore

    //DarkMode flag
    val isDarkThemeEnabled: Flow<Boolean> = dataStore.data.map {
        it[IS_DARK_THEME]
            ?: (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES)
    }

    suspend fun saveDarkMode(isDark: Boolean) {
        dataStore.edit {
            it[IS_DARK_THEME] = isDark
        }
    }

    //Fetch frequency for background worker
    val fetchFrequency = dataStore.data.map {
        it[FETCH_FREQUENCY] ?: 2
    }

    val fetchFrequencyInHours = dataStore.data.map {
        val positionInList = it[FETCH_FREQUENCY] ?: 2
        Constants.HOUR_FREQUENCY_LIST[positionInList]
    }

    suspend fun saveFetchFrequency(positionInList: Int) {
        dataStore.edit {
            it[FETCH_FREQUENCY] = positionInList
        }
    }

    //Units of measurement with enum
    val measurementUnits = dataStore.data.map {
        it[MEASUREMENT_UNITS] ?: 0
    }

    suspend fun saveMeasurementUnits(enumPosition: Int) {
        dataStore.edit {
            it[MEASUREMENT_UNITS] = enumPosition
        }
    }

}