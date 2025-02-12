package ru.serg.weather_elements

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.asTimeZone
import kotlinx.datetime.toJavaZoneOffset
import kotlinx.datetime.toLocalDateTime
import ru.serg.designsystem.theme.customColors
import ru.serg.strings.R.string
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun getHourWithNowAndAccent(timestamp: Long?, offset: Long, color: Color): AnnotatedString {
    return if (timestamp == null) buildAnnotatedString { append("") }
    else {
        val time = Instant.fromEpochMilliseconds(timestamp)
        val date =
            time.toLocalDateTime(TimeZone.of(UtcOffset(seconds = offset.toInt()).asTimeZone().id))
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
                        getFormattedTime(timestamp, offset)
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
                append(getFormattedTime(timestamp, offset))
            }

        }
    }
}

@Composable
fun getFormattedLastUpdateDate(timestamp: Long, offset: Long): String {
    val time = Instant.fromEpochMilliseconds(timestamp)

    val date = time.toLocalDateTime(TimeZone.currentSystemDefault())
    return when {
        (date.dayOfMonth == LocalDateTime.now().dayOfMonth) -> stringResource(
            id = string.today_value,
            getFormattedTime(timestamp, offset)
        )

        (date.dayOfMonth + 1 == LocalDateTime.now().dayOfMonth) -> stringResource(
            id = string.yesterday_value,
            getFormattedTime(timestamp, offset)
        )

        else -> getFormattedTime(timestamp, offset)
    }
}

fun getFormattedTime(timestamp: Long, offset: Long): String {
    val time = timestamp

    val utcOffset = UtcOffset(seconds = offset.toInt())

    val timeZone = java.util.TimeZone.getTimeZone(utcOffset.toJavaZoneOffset())

    return SimpleDateFormat("HH:mm", Locale.getDefault()).apply { this.timeZone = timeZone }
        .format(time)
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

@Composable
fun getWelcomeText() =
    buildAnnotatedString {
        append(stringResource(id = string.welc_app))

        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
            append(stringResource(id = string.first))
        }

        append(" ")

        append(stringResource(id = string.welc_f))

        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
            append(stringResource(id = string.second))
        }

        append(" ")

        append(stringResource(id = string.welc_s))

    }

fun Modifier.animatedBlur(isShowing: Boolean) =
    composed {
        this.blur(
            animateDpAsState(
                targetValue = if (isShowing) 8.dp else 0.dp,
                animationSpec = tween(durationMillis = 200, easing = LinearEasing),
                label = "blur"
            ).value
        )
    }

@Composable
fun getAqiColorByIndex(index: Int): Color =
    when (index) {
        1 -> MaterialTheme.customColors.aqiGoodColor
        2 -> MaterialTheme.customColors.aqiFairColor
        3 -> MaterialTheme.customColors.aqiModerateColor
        4 -> MaterialTheme.customColors.aqiPoorColor
        5 -> MaterialTheme.customColors.aqiVeryPoorColor
        6 -> MaterialTheme.customColors.aqiExtremelyPoorColor
        else -> Color.White
    }
