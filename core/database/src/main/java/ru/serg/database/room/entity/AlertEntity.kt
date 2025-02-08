package ru.serg.database.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.serg.database.Constants

@Entity(
    tableName = Constants.ALERT_TABLE,
)
data class AlertEntity(
    @ColumnInfo(
        name = Constants.CITY_ID,
        index = true
    )
    val cityId: Int,
    val startAt: Long,
    val endAt: Long,
    val title: String,
    val description: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)