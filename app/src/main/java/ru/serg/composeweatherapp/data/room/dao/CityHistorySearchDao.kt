package ru.serg.composeweatherapp.data.room.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.serg.composeweatherapp.data.room.entity.CityEntity
import ru.serg.composeweatherapp.utils.Constants

@Dao
interface CityHistorySearchDao {
    @Query("SELECT * FROM ${Constants.SEARCH_HISTORY}")
    fun citySearchHistoryFlow(): Flow<List<CityEntity>>

    @Query("SELECT * FROM ${Constants.SEARCH_HISTORY}")
    suspend fun citySearchHistory(): List<CityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCityToHistory(cityEntity: CityEntity)

    @Delete
    suspend fun deleteCityFromHistory(cityEntity: CityEntity)
}