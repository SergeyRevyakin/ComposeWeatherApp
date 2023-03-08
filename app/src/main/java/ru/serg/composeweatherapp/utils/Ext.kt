package ru.serg.composeweatherapp.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import ru.serg.composeweatherapp.data.data.CityItem
import ru.serg.composeweatherapp.data.room.entity.CityEntity
import java.util.Locale
import kotlin.math.absoluteValue

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

fun String?.firstLetterToUpperCase() =
    this?.let {
        replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    } ?: Constants.EMPTY_STRING

fun CityItem.toCityEntity() = CityEntity(
    name,
    country,
    latitude,
    longitude,
    isFavorite
)

fun CityEntity.toCityItem() = CityItem(
    cityName,
    country.orEmpty(),
    latitude ?: 0.0,
    longitude ?: 0.0,
    isFavorite
)

infix fun Double?.isNearTo(other: Double?): Boolean {
    if (this == null || other == null) return false
    return when {
        this.minus(other).absoluteValue < 0.001 -> true
        else -> false
    }
}

fun Context.openAppSystemSettings() {
    startActivity(Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        data = Uri.fromParts("package", packageName, null)
    })
}

fun Context.hasLocationPermission(): Boolean {
    return (ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED ||
            (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED)
            )
}

fun Int.hoursToMilliseconds() = this * Constants.Time.millisecondsToHour