package ru.serg.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

class WeatherAlarmManager(private val context: Context) {

    companion object {
        const val ALARM_REQUEST_CODE = 1122
    }

    fun isAlarmSet(): Boolean {
        val intent = Intent(context, FetchWeatherService::class.java)

        val alarm = PendingIntent.getForegroundService(
            context,
            ALARM_REQUEST_CODE,
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
            context, ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + 2 * 60 * 1000L,
            AlarmManager.INTERVAL_FIFTEEN_MINUTES,
//            2*60*1000,
            pendingIntent
        )

    }

    private fun cancelAlarm() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, FetchWeatherService::class.java)
        val pendingIntent = PendingIntent.getForegroundService(
            context, ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
    }
}