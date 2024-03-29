package ru.serg.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import ru.serg.model.Coordinates

class LocationDataSource(
    private val appContext: Context
) : LocationService {

    private var locationManager: LocationManager =
        appContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private val client = LocationServices.getFusedLocationProviderClient(appContext)

    override fun isLocationAvailable(): Boolean {
        if (!appContext.hasLocationPermission()) {
            return false
        }

        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        val isNetworkEnabled =
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        return (isGpsEnabled || isNetworkEnabled)
    }

    @SuppressLint("MissingPermission")
    override fun getLocationUpdate(
        isOneTimeRequest: Boolean,
        updateFrequency: Long
    ): Flow<Coordinates> {
        return callbackFlow {
            if (!appContext.hasLocationPermission()) {
                throw LocationService.LocationException(message = "No location permission")
            }

            val locationManager =
                appContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

            val isNetworkEnabled =
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (!isGpsEnabled && !isNetworkEnabled) {
                throw LocationService.LocationException(message = "GPS is disabled")
            }

            val request = LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                1L
            )
                .setWaitForAccurateLocation(true)
                .setMaxUpdateAgeMillis(1L)
                .build()

            Log.e(this::class.simpleName, "Passed to location callback")
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    Log.e(this::class.simpleName, "Entered callback")
                    super.onLocationResult(result)
                    result.locations
                    result.lastLocation?.let { location ->
                        Log.e(this::class.simpleName, "Got location $location")
                        launch {
                            send(Coordinates(location.latitude, location.longitude))
                        }
                        if (isOneTimeRequest) client.removeLocationUpdates(this)
                    } ?: throw LocationService.LocationException(message = "No location data")
                    Log.e(this::class.simpleName, "Finished callback")
                }
            }

            client.requestLocationUpdates(
                request,
                locationCallback,
                Looper.getMainLooper()
            ).addOnFailureListener {
                Log.e(this::class.simpleName, "Failed")
                close(it)
            }.addOnSuccessListener {
                Log.e(this::class.simpleName, "Success")
            }.addOnCompleteListener {
                Log.e(this::class.simpleName, "Completed")
            }

            awaitClose {
                Log.e(this::class.simpleName, "Closed")
                client.removeLocationUpdates(locationCallback)
            }
        }
    }
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
