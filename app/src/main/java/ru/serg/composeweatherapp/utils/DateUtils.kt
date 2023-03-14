package ru.serg.composeweatherapp.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import io.ktor.util.date.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.serg.composeweatherapp.data.data_source.DataStoreRepository
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.*
import javax.inject.Inject

class DateUtils @Inject constructor(
    val dataStoreRepository: DataStoreRepository
) {

    suspend fun isFetchDateExpired(timestamp: Long): Boolean {
        return dataStoreRepository.fetchFrequency.map {
            (((Constants.HOUR_FREQUENCY_LIST[it]) * 60L * 60L * 1000L + timestamp) - getTimeMillis()) < 0
        }.first()

    }

    companion object {
        fun getHour(l: Long?): String =
            SimpleDateFormat("HH:mm", Locale.getDefault()).format((l ?: 0L))

        fun getTimeWithSeconds(l: Long?): String =
            SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format((l ?: 0L))

        fun getHourWithNow(l: Long?): String {
            val time = Instant.fromEpochMilliseconds(l ?: 0)
            val date = time.toLocalDateTime(TimeZone.currentSystemDefault())
            val now = LocalDateTime.now()
            return when {
                ((l ?: 0) - getTimeMillis() < 60L * 1000L) -> "NOW"
                date.dayOfYear > now.dayOfYear -> {
                    SimpleDateFormat("HH:mm", Locale.getDefault())
                        .format((l ?: 0L)) + " +${date.dayOfYear - now.dayOfYear}"
                }

                else -> SimpleDateFormat("HH:mm", Locale.getDefault()).format((l ?: 0L))
            }

        }

        fun getHourWithNowAndAccent(timestamp: Long?, color: Color): AnnotatedString {
            return if (timestamp == null) buildAnnotatedString { append("") }
            else {
                val time = Instant.fromEpochMilliseconds(timestamp)
                val date = time.toLocalDateTime(TimeZone.currentSystemDefault())
                val now = LocalDateTime.now()
                return when {
                    (timestamp - getTimeMillis() < 60L * 1000L) -> buildAnnotatedString {

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = color)) {
                            append("NOW")
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

        fun getFormattedLastUpdateDate(timestamp: Long): String {
            val time = Instant.fromEpochMilliseconds(timestamp)
            val date = time.toLocalDateTime(TimeZone.currentSystemDefault())
            return when {
                (date.dayOfMonth == LocalDateTime.now().dayOfMonth) -> "Today ${
                    SimpleDateFormat(
                        "HH:mm",
                        Locale.getDefault()
                    ).format(timestamp)
                }"

                (date.dayOfMonth + 1 == LocalDateTime.now().dayOfMonth) -> "Yesterday ${
                    SimpleDateFormat(
                        "HH:mm",
                        Locale.getDefault()
                    ).format(timestamp)
                }"

                else -> SimpleDateFormat("dd.MM, HH:mm", Locale.getDefault()).format(timestamp)
            }
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
    }

}