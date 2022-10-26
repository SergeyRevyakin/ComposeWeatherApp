package ru.serg.composeweatherapp.utils

import android.content.Context
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import io.ktor.util.date.*
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.serg.composeweatherapp.R
import ru.serg.composeweatherapp.data.data.CityItem
import ru.serg.composeweatherapp.data.data.IntraDayTempItem
import ru.serg.composeweatherapp.data.room.entity.CityEntity
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.*

object Ext {

    fun getHour(l: Long?): String = SimpleDateFormat("HH:mm", Locale.getDefault()).format((l ?: 0L))

    fun getHourWithNow(l: Long?): String {
        return if (((l ?: 0)) - getTimeMillis() < 60L * 1000L) "NOW"
        else SimpleDateFormat("HH:mm", Locale.getDefault()).format((l ?: 0L))
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

    fun getMinMaxTemp(temp: IntraDayTempItem?): String {
        return "${temp?.nightTemp?.toInt()}-${temp?.dayTemp?.toInt()}℃"
    }

    fun getTemp(temp: Double?): String {
        return "${temp?.toInt().toString()}℃" //TODO Fahrenheit temp
    }

    fun showNotification(context: Context, header: String?, text: String?) {
        val builder = NotificationCompat.Builder(context, "123")
            .setSmallIcon(R.drawable.ic_sun)
            .setContentTitle(header)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(Random().nextInt(), builder.build())
        }
    }

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    fun String.firstLetterToUpperCase() =
        replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

    fun CityItem.toCityEntity() = CityEntity(
        name,
        country,
        latitude,
        longitude,
        isFavorite
    )

    fun CityEntity.toCityItem() = CityItem(
        cityName,
        country,
        latitude ?: 0.0,
        longitude ?: 0.0,
        isFavorite
    )
}