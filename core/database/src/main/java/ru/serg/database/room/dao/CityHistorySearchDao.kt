package ru.serg.database.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.serg.database.Constants
import ru.serg.database.room.entity.CityEntity

@Dao
interface CityHistorySearchDao {
    @Query("SELECT * FROM ${Constants.CITY_TABLE}")
    fun citySearchHistoryFlow(): Flow<List<CityEntity>>

    @Query("SELECT * FROM ${Constants.CITY_TABLE}")
    suspend fun citySearchHistory(): List<CityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCityToHistory(cityEntity: CityEntity)

    @Delete
    suspend fun deleteCityFromHistory(cityEntity: CityEntity)
}