package ru.serg.composeweatherapp.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.serg.composeweatherapp.data.room.entity.LastLocationEntity
import ru.serg.composeweatherapp.utils.Constants

@Dao
interface LastLocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLocation(lastLocationEntity: LastLocationEntity)

    @Query("SELECT * FROM ${Constants.LAST_LOCATION}")
    suspend fun getLocation(): LastLocationEntity?
}
