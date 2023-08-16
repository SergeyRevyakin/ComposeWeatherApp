package ru.serg.composeweatherapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.serg.composeweatherapp.R
import ru.serg.datastore.DataStoreDataSource
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

class DateUtils @Inject constructor(
    val dataStoreDataSource: DataStoreDataSource
) {

    suspend fun isFetchDateExpired(timestamp: Long): Boolean {
        return dataStoreDataSource.fetchFrequency.map {
            (((Constants.HOUR_FREQUENCY_LIST[it]) * 60L * 60L * 1000L + timestamp) - getTimeMillis()) < 0
        }.first()

    }

    companion object {
        fun getHour(l: Long?): String =
            SimpleDateFormat("HH:mm", Locale.getDefault()).format((l ?: 0L))

        @Composable
        fun getHourWithNowAndAccent(timestamp: Long?, color: Color): AnnotatedString {
            return if (timestamp == null) buildAnnotatedString { append("") }
            else {
                val time = Instant.fromEpochMilliseconds(timestamp)
                val date = time.toLocalDateTime(TimeZone.currentSystemDefault())
                val now = LocalDateTime.now()
                return when {
                    (timestamp - getTimeMillis() < 60L * 1000L) -> buildAnnotatedString {

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = color)) {
                            append(stringResource(id = R.string.now).uppercase())
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
                    id = R.string.today_value,
                    SimpleDateFormat("HH:mm", Locale.getDefault()).format(timestamp)
                )

                (date.dayOfMonth + 1 == LocalDateTime.now().dayOfMonth) -> stringResource(
                    id = R.string.yesterday_value,
                    SimpleDateFormat("HH:mm", Locale.getDefault()).format(timestamp)
                )

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