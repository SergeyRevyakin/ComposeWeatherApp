package ru.serg.composeweatherapp.service

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.serg.composeweatherapp.R
import ru.serg.composeweatherapp.data.WeatherServiceUseCase
import ru.serg.composeweatherapp.data.data.WeatherItem
import ru.serg.composeweatherapp.utils.Constants
import ru.serg.composeweatherapp.utils.DateUtils
import ru.serg.composeweatherapp.utils.ServiceFetchingResult
import javax.inject.Inject

@AndroidEntryPoint
class FetchWeatherService : Service() {

    @Inject
    lateinit var weatherServiceUseCase: WeatherServiceUseCase

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            START_ACTION -> start()
            STOP_ACTION -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val serviceNotification =
            NotificationCompat.Builder(this, Constants.Notifications.NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Fetching weather data...")
                .setContentText("Starting...")
                .setSmallIcon(R.drawable.ic_day_sunny)
                .setOngoing(true)

        weatherServiceUseCase.checkCurrentLocationAndWeather()
            .onEach {
                when (it) {
                    is ServiceFetchingResult.Success -> {
                        onWeatherFetchedSuccessful(it.data)
                    }

                    is ServiceFetchingResult.Error -> {
                        val updatedNotification =
                            serviceNotification.setContentText("Error ${it.message}")
                        notificationManager.notify(SERVICE_ID, updatedNotification.build())
                    }

                    is ServiceFetchingResult.Loading -> {}
                }
            }.launchIn(serviceScope)

        startForeground(SERVICE_ID, serviceNotification.build())

    }

    private fun onWeatherFetchedSuccessful(weatherItem: WeatherItem) {
        stop()
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val weatherNotification =
            NotificationCompat.Builder(this, Constants.Notifications.NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Weather for ${DateUtils.getDate(weatherItem.lastUpdatedTime)} in ${weatherItem.cityItem?.name}")
                .setContentText(
                    "Right now feels like: ${weatherItem.feelsLike} " +
                            "\nMorning: ${weatherItem.dailyWeatherList.first().temp.morningTemp}" +
                            "\nDay: ${weatherItem.dailyWeatherList.first().temp.dayTemp}"
                )
                .setSmallIcon(R.drawable.ic_day_sunny)
                .build()

        notificationManager.notify(WEATHER_NOTIFICATION_ID, weatherNotification)

    }

    private fun stop() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf(SERVICE_ID)
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    companion object {
        const val START_ACTION = "start"
        const val STOP_ACTION = "stop"
        private const val SERVICE_ID = 222
        private const val WEATHER_NOTIFICATION_ID = 1122
    }
}