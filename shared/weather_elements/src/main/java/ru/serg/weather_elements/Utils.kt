package ru.serg.weather_elements

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.serg.strings.R.string
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun getHourWithNowAndAccent(timestamp: Long?, color: Color): AnnotatedString {
    return if (timestamp == null) buildAnnotatedString { append("") }
    else {
        val time = Instant.fromEpochMilliseconds(timestamp)
        val date = time.toLocalDateTime(TimeZone.currentSystemDefault())
        val now = LocalDateTime.now()
        return when {
            (timestamp - System.currentTimeMillis() < 60L * 1000L) -> buildAnnotatedString {

                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = color)) {
                    append(stringResource(id = string.now).uppercase())
                }
            }

            date.dayOfYear > now.dayOfYear -> {
                buildAnnotatedString {
                    append(
                        SimpleDateFormat("HH:mm", Locale.getDefault())
                            .format(timestamp)
                    )
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = color
                        )
                    ) {
                        append(" +${date.dayOfYear - now.dayOfYear}")
                    }
                }
            }

            else -> buildAnnotatedString {
                append(SimpleDateFormat("HH:mm", Locale.getDefault()).format(timestamp))
            }

        }
    }
}

@Composable
fun getFormattedLastUpdateDate(timestamp: Long): String {
    val time = Instant.fromEpochMilliseconds(timestamp)
    val date = time.toLocalDateTime(TimeZone.currentSystemDefault())
    return when {
        (date.dayOfMonth == LocalDateTime.now().dayOfMonth) -> stringResource(
            id = string.today_value,
            SimpleDateFormat("HH:mm", Locale.getDefault()).format(timestamp)
        )

        (date.dayOfMonth + 1 == LocalDateTime.now().dayOfMonth) -> stringResource(
            id = string.yesterday_value,
            SimpleDateFormat("HH:mm", Locale.getDefault()).format(timestamp)
        )

        else -> SimpleDateFormat("dd.MM, HH:mm", Locale.getDefault()).format(timestamp)
    }
}

fun getFullDate(timestamp: Long?): AnnotatedString {
    return if (timestamp == null) buildAnnotatedString { append("") }
    else {
        val time = Instant.fromEpochMilliseconds(timestamp)
        val local = time.toLocalDateTime(TimeZone.currentSystemDefault())
        buildAnnotatedString {

            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(
                    "${
                        local.dayOfWeek.getDisplayName(
                            TextStyle.FULL_STANDALONE,
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

fun buildTitle(temp: String?, description: String?) = buildAnnotatedString {

    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
        append(
            temp
        )
        append(", ")
    }

    append(description)
}

fun String?.firstLetterToUpperCase() =
    this?.let {
        replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    } ?: ""

fun getHour(l: Long?): String =
    SimpleDateFormat("HH:mm", Locale.getDefault()).format((l ?: 0L))


