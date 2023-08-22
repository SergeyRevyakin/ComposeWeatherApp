package ru.serg.designsystem.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.serg.model.IntraDayTempItem
import ru.serg.model.UpdatedDailyTempItem
import java.time.format.TextStyle
import java.util.Locale

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

fun getDate(timestamp: Long?): AnnotatedString {
    return if (timestamp == null) buildAnnotatedString { append("") }
    else {
        val time = Instant.fromEpochMilliseconds(timestamp)
        val local = time.toLocalDateTime(TimeZone.currentSystemDefault())
        buildAnnotatedString {

            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(
                    "${
                        local.dayOfWeek.getDisplayName(
                            TextStyle.SHORT,
                            Locale.getDefault()
                        )
                    }, "
                )
            }

            append(
                "${local.dayOfMonth} ${
                    local.month.getDisplayName(
                        TextStyle.SHORT,
                        Locale.getDefault()
                    )
                }"
            )
        }
    }
}

fun emptyString() = Constants.EMPTY_STRING

fun buildTitle(temp: String?, description: String?) = buildAnnotatedString {

    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
        append(
            temp
        )
        append(", ")
    }

    append(description)
}

fun Int.hoursToMilliseconds() = this * Constants.Time.millisecondsToHour