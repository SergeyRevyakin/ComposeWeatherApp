package ru.serg.composeweatherapp.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Looper
import android.provider.Settings
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import io.ktor.util.date.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import ru.serg.composeweatherapp.data.data.CityItem
import ru.serg.composeweatherapp.data.data.CoordinatesWrapper
import ru.serg.composeweatherapp.data.data.IntraDayTempItem
import ru.serg.composeweatherapp.data.room.entity.CityEntity
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.absoluteValue

object Ext {

    fun getMinMaxTemp(temp: IntraDayTempItem?): String {
        return "${temp?.nightTemp?.toInt()}-${temp?.dayTemp?.toInt()}℃"
    }

    fun getTemp(temp: Double?): String {
        return "${temp?.toInt().toString()}℃" //TODO Fahrenheit temp
    }

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    fun String.firstLetterToUpperCase() =
        replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

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

    @SuppressLint("MissingPermission")
    fun FusedLocationProviderClient.locationFlow(): Flow<CoordinatesWrapper> =
        callbackFlow {
            val locationRequest = LocationRequest.create().apply {
                interval = TimeUnit.SECONDS.toMillis(15_000)
                fastestInterval = TimeUnit.SECONDS.toMillis(1_000)
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }
            val callBack = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    val location = locationResult.lastLocation
                    val userLocation = CoordinatesWrapper(
                        latitude = location?.latitude ?: 0.0,
                        longitude = location?.longitude ?: 0.0
                    )
                    try {
                        this@callbackFlow.trySend(userLocation).isSuccess
                        removeLocationUpdates(this)
                    } catch (_: Exception) {
                    }
                }
            }

            requestLocationUpdates(
                locationRequest,
                callBack,
                Looper.getMainLooper()
            ).addOnFailureListener { e ->
                close(e)
            }
            awaitClose {
                removeLocationUpdates(callBack)
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
}