package ru.serg.composeweatherapp.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import io.ktor.util.date.*
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.serg.composeweatherapp.data.remote.responses.OneCallResponse
import java.text.SimpleDateFormat
import java.time.format.TextStyle
import java.util.*

object Ext {

    fun getCountry(countyCode: String): String =
        Locale(countyCode).displayCountry


    fun getHour(l: Long?): String {
        return if (((l ?: 0) * 1000L) - getTimeMillis() < 60L * 1000L) "NOW"
        else SimpleDateFormat("HH", Locale.getDefault()).format((l ?: 0L) * 1000L)
    }

    fun getDate(timestamp: Long?): AnnotatedString {
        return if (timestamp == null) buildAnnotatedString { append("") }
        else {
            val time = Instant.fromEpochMilliseconds(timestamp * 1000L)
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

    fun getMinMaxTemp(temp: OneCallResponse.Daily.Temp?): String {
        return "${temp?.min?.toInt()}-${temp?.max?.toInt()}"
    }

}