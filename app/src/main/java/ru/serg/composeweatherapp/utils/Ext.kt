package ru.serg.composeweatherapp.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import io.ktor.util.date.getTimeMillis
import ru.serg.database.room.entity.CityEntity
import ru.serg.model.CityItem
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
    isFavorite,
    if (isFavorite) -1 else id,
    lastTimeUpdated = getTimeMillis()
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

@RequiresApi(Build.VERSION_CODES.S)
fun Context.setExactAlarm() {
    startActivity(Intent().apply {
        action = Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
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

fun Double?.orZero() = this ?: 0.0

fun Int?.orZero() = this ?: 0

fun Long?.orZero() = this ?: 0

fun String?.orEmpty() = this ?: ""

fun Int?.toTimeStamp() = this?.let { it * 1000L } ?: 0L

fun Long?.toTimeStamp() = this?.let { it * 1000L } ?: 0L