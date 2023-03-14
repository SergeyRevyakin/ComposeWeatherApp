package ru.serg.composewetherapp.data_source.remote

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.serg.composeweatherapp.data.data_source.IDataStoreDataSource

class FakeDataStoreDataSource : IDataStoreDataSource {
    override val measurementUnits: Flow<Int> = flow {
        emit(1)
    }
}