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
import ru.serg.database.room.entity.UpdatedCityWeather
import ru.serg.database.room.entity.UpdatedDailyWeatherEntity
import ru.serg.database.room.entity.UpdatedHourlyWeatherEntity
import ru.serg.database.room.entity.WeatherItemEntity
import ru.serg.database.room.entity.WeatherWithCity
import ru.serg.model.CityItem
import ru.serg.model.DailyWeather
import ru.serg.model.HourlyWeather
import ru.serg.model.UpdatedWeatherItem
import ru.serg.model.WeatherItem
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

fun CityEntity.toCityItem() = CityItem(
    cityName,
    country.orEmpty(),
    latitude ?: 0.0,
    longitude ?: 0.0,
    isFavorite,
    id,
    lastTimeUpdated.orZero()
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

fun WeatherWithCity.toWeatherItem() = WeatherItem(
    feelsLike = weatherItemEntity.feelsLike,
    currentTemp = weatherItemEntity.currentTemp,
    windDirection = weatherItemEntity.windDirection,
    windSpeed = weatherItemEntity.windSpeed,
    humidity = weatherItemEntity.humidity,
    pressure = weatherItemEntity.pressure,
    weatherDescription = weatherItemEntity.weatherDescription,
    weatherIcon = weatherItemEntity.weatherIcon,
    dateTime = weatherItemEntity.dateTime,
    cityItem = CityItem(
        cityEntity?.cityName.orEmpty(),
        cityEntity?.country.orEmpty(),
        cityEntity?.latitude ?: 0.0,
        cityEntity?.longitude ?: 0.0,
        cityEntity?.isFavorite ?: false
    ),
    lastUpdatedTime = weatherItemEntity.lastUpdatedTime,
    hourlyWeatherList = weatherItemEntity.hourlyWeatherList.list.filter { hourWeatherItem ->
        hourWeatherItem.timestamp > getTimeMillis()
    },
    dailyWeatherList = weatherItemEntity.dailyWeatherList.list.filter { dayWeatherItem ->
        dayWeatherItem.dateTime > getTimeMillis()
    }
)

fun WeatherItem.toWeatherEntity() = WeatherItemEntity(
    feelsLike = feelsLike,
    currentTemp = currentTemp,
    windDirection = windDirection,
    windSpeed = windSpeed,
    humidity = humidity,
    pressure = pressure,
    weatherDescription = weatherDescription,
    weatherIcon = weatherIcon,
    dateTime = dateTime,
    cityName = cityItem?.name.orEmpty(),
    lastUpdatedTime = lastUpdatedTime,
    hourlyWeatherList = WeatherItemEntity.HourItemList(
        hourlyWeatherList
    ),
    dailyWeatherList = WeatherItemEntity.DailyItemList(
        dailyWeatherList
    )
)

fun UpdatedHourlyWeatherEntity.toHourlyWeather() = HourlyWeather(
    windSpeed = windSpeed.orZero(),
    windDirection = windDirection.orZero(),
    weatherIcon = weatherIcon.orZero(),
    weatherDescription = weatherDescription.orEmpty(),
    feelsLike = feelsLike.orZero(),
    currentTemp = currentTemp.orZero(),
    humidity = humidity.orZero(),
    pressure = pressure.orZero(),
    dateTime = dateTime.orZero(),
    uvi = uvi.orZero()
)

fun UpdatedDailyWeatherEntity.toDailyWeather() = DailyWeather(
    windSpeed = windSpeed.orZero(),
    windDirection = windDirection.orZero(),
    weatherIcon = weatherIcon.orZero(),
    weatherDescription = weatherDescription.orEmpty(),
    humidity = humidity.orZero(),
    pressure = pressure.orZero(),
    dateTime = dateTime.orZero(),
    feelsLike = feelsLike,
    dailyWeatherItem = dailyWeatherItem,
    sunrise = sunrise.orZero(),
    sunset = sunset.orZero(),
    uvi = uvi.orZero()
)

fun UpdatedCityWeather.toWeatherItem() = UpdatedWeatherItem(
    cityItem = cityEntity.toCityItem(),
    hourlyWeatherList = hourlyWeatherEntity.map {
        it.toHourlyWeather()
    },
    dailyWeatherList = updatedDailyWeatherEntity.map {
        it.toDailyWeather()
    }
)

fun Double?.orZero() = this ?: 0.0

fun Int?.orZero() = this ?: 0

fun Long?.orZero() = this ?: 0

fun String?.orEmpty() = this ?: ""

fun Int?.toTimeStamp() = this?.let { it * 1000L } ?: 0L

fun Long?.toTimeStamp() = this?.let { it * 1000L } ?: 0L