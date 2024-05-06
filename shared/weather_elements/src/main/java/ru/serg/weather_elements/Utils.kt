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

fun getHour(l: Long?): String =
    SimpleDateFormat("HH:mm", Locale.getDefault()).format((l ?: 0L))

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
fun getAqiStringByIndex(index: Int): String =
    when (index) {
        1 -> stringResource(id = string.aqi_good)
        2 -> stringResource(id = string.aqi_fair)
        3 -> stringResource(id = string.aqi_moderate)
        4 -> stringResource(id = string.aqi_poor)
        5 -> stringResource(id = string.aqi_very_poor)
        else -> ""
    }


