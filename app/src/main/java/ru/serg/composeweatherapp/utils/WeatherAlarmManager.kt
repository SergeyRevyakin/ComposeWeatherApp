package ru.serg.composeweatherapp.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import io.ktor.util.date.getTimeMillis
import ru.serg.composeweatherapp.service.FetchWeatherService

class WeatherAlarmManager(private val context: Context) {

    fun isAlarmSet(): Boolean {
        val intent = Intent(context, FetchWeatherService::class.java)

        val alarm = PendingIntent.getForegroundService(
            context,
            Constants.ALARM_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_NO_CREATE
        )

        return (alarm != null)

    }


    fun setOrCancelAlarm() {
        if (isAlarmSet()) {
            cancelAlarm()
        } else {
            setAlarm()
        }
    }

    private fun setAlarm() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, FetchWeatherService::class.java)
        val pendingIntent = PendingIntent.getForegroundService(
            context, Constants.ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            getTimeMillis(),
            AlarmManager.INTERVAL_FIFTEEN_MINUTES,
            pendingIntent
        )

    }

    private fun cancelAlarm() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, FetchWeatherService::class.java)
        val pendingIntent = PendingIntent.getForegroundService(
            context, Constants.ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
    }
}