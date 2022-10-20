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
import ru.serg.composeweatherapp.data.remote.responses.OneCallResponse
import java.text.SimpleDateFormat
import java.time.format.TextStyle
import java.util.*

object Ext {

    fun getHour(l: Long?): String {
        return if (((l ?: 0) * 1000L) - getTimeMillis() < 60L * 1000L) "NOW"
        else SimpleDateFormat("HH:mm", Locale.getDefault()).format((l ?: 0L) * 1000L)
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

    fun getFullDate(timestamp: Long?): AnnotatedString {
        return if (timestamp == null) buildAnnotatedString { append("") }
        else {
            val time = Instant.fromEpochMilliseconds(timestamp * 1000L)
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
                            TextStyle.FULL,
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

    fun getTemp(temp: Double?): String {
        return "${temp?.toInt().toString()}℃" //TODO Fahrenheit temp
    }

    fun showNotification(context: Context, header:String?, text:String?){
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
}