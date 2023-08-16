package ru.serg.datastore

import android.content.Context
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class DataStoreDataSource(context: Context) {
    companion object {
        val IS_DARK_THEME = booleanPreferencesKey(Constants.DataStore.IS_DARK_THEME)
        val FETCH_FREQUENCY = intPreferencesKey(Constants.DataStore.FETCH_FREQUENCY)
        val MEASUREMENT_UNITS = intPreferencesKey(Constants.DataStore.MEASUREMENT_UNITS)
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private val dataStore = context.dataStore

    //DarkMode flag
    val isDarkThemeEnabled: Flow<Boolean> = dataStore.data.map {
        it[IS_DARK_THEME]
            ?: (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES)
    }.distinctUntilChanged()

    suspend fun saveDarkMode(isDark: Boolean) {
        dataStore.edit {
            it[IS_DARK_THEME] = isDark
        }
    }

    //Fetch frequency for background worker
    val fetchFrequency = dataStore.data.map {
        it[FETCH_FREQUENCY] ?: 2
    }.distinctUntilChanged()

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
    }.distinctUntilChanged()

    suspend fun saveMeasurementUnits(enumPosition: Int) {
        dataStore.edit {
            it[MEASUREMENT_UNITS] = enumPosition
        }
    }

}