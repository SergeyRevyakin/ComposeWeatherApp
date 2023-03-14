package ru.serg.composeweatherapp.data.data_source

import kotlinx.coroutines.flow.Flow

interface IDataStoreDataSource {

    val measurementUnits: Flow<Int>

}