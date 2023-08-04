package ru.serg.composeweatherapp.utils

import android.os.Build
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.serg.model.IntraDayTempItem
import com.serg.model.UpdatedDailyTempItem

fun isSavedDataExpired(lastUpdateTime: Long, delayBeforeExpireHours: Int) =
    lastUpdateTime < (System.currentTimeMillis() - delayBeforeExpireHours.hoursToMilliseconds())

fun getMinMaxTemp(temp: IntraDayTempItem?, units: String): String {
    return "${temp?.nightTemp?.toInt()}-${temp?.dayTemp?.toInt()}$units"
}

fun getMinMaxTemp(temp: UpdatedDailyTempItem, units: String): String {
    return "${temp.minTemp?.toInt()}-${temp.maxTemp?.toInt()}$units"
}

fun getTemp(temp: Double?, units: String): String {
    return "${temp?.toInt().toString()}$units"
}

fun emptyString() = Constants.EMPTY_STRING

fun isTiramisuOrAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

fun buildTitle(temp: String?, description: String?) = buildAnnotatedString {

    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
        append(
            temp
        )
        append(", ")
    }

    append(description)
}