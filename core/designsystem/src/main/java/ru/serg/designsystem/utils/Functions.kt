package ru.serg.designsystem.utils

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.serg.model.UpdatedDailyTempItem
import java.time.format.TextStyle
import java.util.Locale
import java.util.UUID

fun getMinMaxTemp(temp: UpdatedDailyTempItem, units: String): String {
    return "${temp.minTemp?.toInt()}-${temp.maxTemp?.toInt()}$units"
}

fun getTemp(temp: Double?, units: String): String {
    return "${temp?.toInt().toString()}$units"
}

fun getTemp(temp: Int?, units: String): String {
    return "${temp?.toString()}$units"
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

fun String?.firstLetterToUpperCase() =
    this?.let {
        replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    } ?: ""

@Composable
fun <S> AnimWeather(
    targetState: S,
    content: @Composable AnimatedContentScope.(targetState: S) -> Unit
) = AnimatedContent(
    targetState = targetState, label = "AnimWeather",
    transitionSpec = {
        fadeIn(
            animationSpec = tween(
                durationMillis = 300,
                delayMillis = 300
            )
        ) togetherWith fadeOut(animationSpec = tween(durationMillis = 300))
    },
    content = content
)

@Preview
@Composable
fun PreviewAnimWeather() {

    AnimWeather(targetState = 123.0) {
        Text(
            text = buildTitle(
                getTemp(
                    temp = it,
                    "C"
                ), UUID.randomUUID().toString()
            ),
            textAlign = TextAlign.End,
            fontSize = 28.sp,
            color = Color.Green,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}