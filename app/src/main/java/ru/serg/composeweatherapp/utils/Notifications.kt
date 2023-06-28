package ru.serg.composeweatherapp.utils

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import ru.serg.composeweatherapp.MainActivity
import ru.serg.composeweatherapp.R
import ru.serg.composeweatherapp.data.dto.WeatherItem
import java.util.Random


@SuppressLint("MissingPermission") //TODO add notification check for Android 13
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

@SuppressLint("MissingPermission") //TODO add notification check for Android 13
fun showDailyForecastNotification(context: Context, weatherItem: WeatherItem) {

    val weatherXml = weatherItem.weatherIcon ?: R.drawable.ic_sun
    val img = getBitmapFromVectorDrawable(context, weatherXml)

    val notificationIntent = Intent(context, MainActivity::class.java)
        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
    val pendingIntent = PendingIntent.getActivity(
        context, 0, notificationIntent,
        PendingIntent.FLAG_MUTABLE
    )

    val builder =
        NotificationCompat.Builder(context, Constants.Notifications.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(weatherXml)
            .setContentTitle("Weather for ${DateUtils.getDate(weatherItem.lastUpdatedTime)}")
            .setContentText("In ${weatherItem.cityItem?.name}")
            .setLargeIcon(img)
            .setStyle(
                NotificationCompat.InboxStyle()
                    .addLine("Right now: ${weatherItem.feelsLike}")
                    .addLine("Morning: ${weatherItem.dailyWeatherList.first().temp.morningTemp}")
                    .addLine("Day: ${weatherItem.dailyWeatherList.first().temp.dayTemp}")
                    .addLine("Evening: ${weatherItem.dailyWeatherList.first().temp.eveningTemp}")
                    .addLine("Night: ${weatherItem.dailyWeatherList.first().temp.nightTemp}")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setColor(ContextCompat.getColor(context, R.color.primary))
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

    with(NotificationManagerCompat.from(context)) {
        notify(Random().nextInt(), builder.build())
    }
}
@SuppressLint("MissingPermission") //TODO add notification check for Android 13
fun showDailyServiceForecastNotification(context: Context, weatherItem: WeatherItem) {

    val weatherXml = weatherItem.weatherIcon ?: R.drawable.ic_sun
    val img = getBitmapFromVectorDrawable(context, weatherXml)

    val notificationIntent = Intent(context, MainActivity::class.java)
        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
    val pendingIntent = PendingIntent.getActivity(
        context, 0, notificationIntent,
        PendingIntent.FLAG_MUTABLE
    )

    val builder =
        NotificationCompat.Builder(context, Constants.Notifications.NOTIFICATION_CHANNEL_SERVICE_ID)
            .setSmallIcon(weatherXml)
            .setContentTitle("Weather Service for ${DateUtils.getDate(weatherItem.lastUpdatedTime)}")
            .setContentText("In ${weatherItem.cityItem?.name}")
            .setLargeIcon(img)
            .setStyle(
                NotificationCompat.InboxStyle()
                    .addLine("Right now: ${weatherItem.feelsLike}")
                    .addLine("Morning: ${weatherItem.dailyWeatherList.first().temp.morningTemp}")
                    .addLine("Day: ${weatherItem.dailyWeatherList.first().temp.dayTemp}")
                    .addLine("Evening: ${weatherItem.dailyWeatherList.first().temp.eveningTemp}")
                    .addLine("Night: ${weatherItem.dailyWeatherList.first().temp.nightTemp}")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setColor(ContextCompat.getColor(context, R.color.primary))
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

    with(NotificationManagerCompat.from(context)) {
        notify(Random().nextInt(), builder.build())
    }
}

@SuppressLint("MissingPermission") //TODO add notification check for Android 13
fun showFetchErrorNotification(context: Context, errorText: String?) {

    val notificationIntent = Intent(context, MainActivity::class.java)
        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
    val pendingIntent = PendingIntent.getActivity(
        context, 0, notificationIntent,
        PendingIntent.FLAG_IMMUTABLE
    )

    val builder =
        NotificationCompat.Builder(context, Constants.Notifications.NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Not able to fetch weather")
            .setContentText(errorText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSmallIcon(R.drawable.ic_sunny_day)
            .setColor(ContextCompat.getColor(context, R.color.primary))
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

    with(NotificationManagerCompat.from(context)) {
        notify(Random().nextInt(), builder.build())
    }
}

fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap {
    val drawable = ContextCompat.getDrawable(context, drawableId)
    drawable?.setTint(ContextCompat.getColor(context, R.color.primary))


    val bitmap = Bitmap.createBitmap(
        drawable!!.intrinsicWidth,
        drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}