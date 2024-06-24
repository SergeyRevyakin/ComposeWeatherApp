package ru.serg.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import ru.serg.model.CityItem

@Parcelize
@Serializable
data class ParcCityItem(
    val name: String,
    val country: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val isFavorite: Boolean = false,
    val id: Int = 0,
    val lastTimeUpdated: Long = 0L
) : Parcelable

fun ParcCityItem.toCityItem() = CityItem(
    name = this.name,
    country = this.country,
    latitude = this.latitude,
    longitude = this.longitude,
    isFavorite = this.isFavorite,
    id = this.id,
    lastTimeUpdated = this.lastTimeUpdated
)


fun CityItem.toParcCityItem() = ParcCityItem(
    name = this.name,
    country = this.country,
    latitude = this.latitude,
    longitude = this.longitude,
    isFavorite = this.isFavorite,
    id = this.id,
    lastTimeUpdated = this.lastTimeUpdated
)