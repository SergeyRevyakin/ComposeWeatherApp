package ru.serg.notifications

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.serg.common.R.color
import ru.serg.drawables.R.drawable
import ru.serg.model.WeatherItem
import ru.serg.notifications.Constants.Notifications.NOTIFICATION_ERROR_REQUEST_CODE
import ru.serg.notifications.Constants.Notifications.NOTIFICATION_REQUEST_CODE
import ru.serg.notifications.Constants.Notifications.TARGET_ACTIVITY_NAME
import java.time.format.TextStyle
import java.util.Locale
import java.util.Random


@SuppressLint("MissingPermission")
fun showNotification(context: Context, header: String?, text: String?) {
    val builder = NotificationCompat.Builder(
        context,
        Constants.Notifications.NOTIFICATION_CHANNEL_ID
    )
        .setSmallIcon(drawable.ic_sunny_day)
        .setContentTitle(header)
        .setContentText(text)
        .setPriority(NotificationCompat.PRIORITY_MAX)
        .setAutoCancel(true)

    with(NotificationManagerCompat.from(context)) {
        notify(Random().nextInt(), builder.build())
    }
}

@SuppressLint("MissingPermission")
fun showDailyForecastNotification(context: Context, weatherItem: WeatherItem) {

    val weatherXml =
        weatherItem.hourlyWeatherList.firstOrNull()?.weatherIcon ?: drawable.ic_sunny_day
    val img = getBitmapFromVectorDrawable(context, weatherXml)


    val pendingIntent = PendingIntent.getActivity(
        context,
        NOTIFICATION_REQUEST_CODE,
        Intent().apply {
            action = Intent.ACTION_VIEW
            component = ComponentName(
                context.packageName,
                TARGET_ACTIVITY_NAME,
            )
        },
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
    )

    val builder =
        NotificationCompat.Builder(context, Constants.Notifications.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(weatherXml)
            .setContentTitle("Weather for ${getDate(weatherItem.cityItem.lastTimeUpdated)}")
            .setContentText("In ${weatherItem.cityItem.name}")
            .setLargeIcon(img)
            .setStyle(
                NotificationCompat.InboxStyle()
                    .addLine("Right now: ${weatherItem.hourlyWeatherList.first().feelsLike}")
                    .addLine("Morning: ${weatherItem.dailyWeatherList.first().dailyWeatherItem.morningTemp}")
                    .addLine("Day: ${weatherItem.dailyWeatherList.first().dailyWeatherItem.dayTemp}")
                    .addLine("Evening: ${weatherItem.dailyWeatherList.first().dailyWeatherItem.eveningTemp}")
                    .addLine("Night: ${weatherItem.dailyWeatherList.first().dailyWeatherItem.nightTemp}")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setColor(ContextCompat.getColor(context, color.primary))
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

    with(NotificationManagerCompat.from(context)) {
        notify(Random().nextInt(), builder.build())
    }
}

@SuppressLint("MissingPermission")
fun showDailyServiceForecastNotification(context: Context, weatherItem: WeatherItem) {

    val weatherXml =
        weatherItem.dailyWeatherList.firstOrNull()?.weatherIcon ?: drawable.ic_sunny_day
    val img = getBitmapFromVectorDrawable(context, weatherXml)

    val pendingIntent = PendingIntent.getActivity(
        context,
        NOTIFICATION_REQUEST_CODE,
        Intent().apply {
            action = Intent.ACTION_VIEW
            component = ComponentName(
                context.packageName,
                TARGET_ACTIVITY_NAME,
            )
        },
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
    )

    val builder =
        NotificationCompat.Builder(context, Constants.Notifications.NOTIFICATION_CHANNEL_SERVICE_ID)
            .setSmallIcon(weatherXml)
            .setContentTitle("Weather Service for ${getDate(weatherItem.cityItem.lastTimeUpdated)}")
            .setContentText("In ${weatherItem.cityItem.name}")
            .setLargeIcon(img)
            .setStyle(
                NotificationCompat.InboxStyle()
                    .addLine("Right now: ${weatherItem.hourlyWeatherList.first().feelsLike}")
                    .addLine("Morning: ${weatherItem.dailyWeatherList.first().dailyWeatherItem.morningTemp}")
                    .addLine("Day: ${weatherItem.dailyWeatherList.first().dailyWeatherItem.dayTemp}")
                    .addLine("Evening: ${weatherItem.dailyWeatherList.first().dailyWeatherItem.eveningTemp}")
                    .addLine("Night: ${weatherItem.dailyWeatherList.first().dailyWeatherItem.nightTemp}")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setColor(ContextCompat.getColor(context, color.primary))
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

    with(NotificationManagerCompat.from(context)) {
        notify(Random().nextInt(), builder.build())
    }
}

@SuppressLint("MissingPermission")
fun showFetchErrorNotification(context: Context, errorText: String?) {

    val pendingIntent = PendingIntent.getActivity(
        context,
        NOTIFICATION_ERROR_REQUEST_CODE,
        Intent().apply {
            action = Intent.ACTION_VIEW
            component = ComponentName(
                context.packageName,
                TARGET_ACTIVITY_NAME,
            )
        },
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
    )


    val builder =
        NotificationCompat.Builder(context, Constants.Notifications.NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Not able to fetch weather")
            .setContentText(errorText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSmallIcon(drawable.ic_sunny_day)
            .setColor(ContextCompat.getColor(context, color.primary))
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

    with(NotificationManagerCompat.from(context)) {
        notify(Random().nextInt(), builder.build())
    }
}

fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap {
    val drawable = ContextCompat.getDrawable(context, drawableId)
    drawable?.setTint(ContextCompat.getColor(context, color.primary))


    val bitmap = Bitmap.createBitmap(
        drawable!!.intrinsicWidth,
        drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
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
