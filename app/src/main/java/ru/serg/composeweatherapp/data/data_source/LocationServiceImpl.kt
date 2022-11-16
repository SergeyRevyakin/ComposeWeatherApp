package ru.serg.composeweatherapp.data.data_source

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import dagger.assisted.Assisted
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import ru.serg.composeweatherapp.data.data.CoordinatesWrapper
import ru.serg.composeweatherapp.utils.Ext.hasLocationPermission

class LocationServiceImpl(
    @Assisted val appContext: Context,
    private val client: FusedLocationProviderClient
) : LocationService {

    @SuppressLint("MissingPermission")
    override fun getLocationUpdate(): Flow<CoordinatesWrapper> {
        return callbackFlow {
            if (!appContext.hasLocationPermission()) {
                throw LocationService.LocationException("No location permission")
            }

            val locationManager =
                appContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

            val isNetworkEnabled =
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (!isGpsEnabled && !isNetworkEnabled) {
                throw LocationService.LocationException("GPS is disabled")
            }

            val request = LocationRequest.create()
                .setInterval(15_000)

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)
                    result.lastLocation?.let { location ->
                        launch {
                            send(CoordinatesWrapper(location.latitude, location.longitude))
                        }
                    }
                }
            }

            client.requestLocationUpdates(
                request,
                locationCallback,
                Looper.getMainLooper()
            )

            awaitClose {
                client.removeLocationUpdates(locationCallback)
            }
        }
    }
}