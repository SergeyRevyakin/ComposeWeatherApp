package ru.serg.composeweatherapp.utils

import android.os.Build
import ru.serg.composeweatherapp.data.dto.IntraDayTempItem

fun isSavedDataExpired(lastUpdateTime: Long, delayBeforeExpireHours: Int) =
    lastUpdateTime < (System.currentTimeMillis() - delayBeforeExpireHours.hoursToMilliseconds())

fun getMinMaxTemp(temp: IntraDayTempItem?, units: String): String {
    return "${temp?.nightTemp?.toInt()}-${temp?.dayTemp?.toInt()}$units"
}

fun getTemp(temp: Double?, units: String): String {
    return "${temp?.toInt().toString()}$units"
}

fun emptyString() = Constants.EMPTY_STRING

fun isTiramisuOrAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU