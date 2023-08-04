package ru.serg.composeweatherapp.service

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION
import android.os.Build
import android.os.IBinder
import android.util.Log
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
import ru.serg.composeweatherapp.utils.Constants
import ru.serg.composeweatherapp.utils.ServiceFetchingResult
import ru.serg.composeweatherapp.utils.common.showDailyServiceForecastNotification
import ru.serg.model.WeatherItem
import javax.inject.Inject

@AndroidEntryPoint
class FetchWeatherService : Service() {

    @Inject
    lateinit var weatherServiceUseCase: WeatherServiceUseCase

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        when (intent?.action) {
//            START_ACTION -> start()
//            STOP_ACTION -> stop()
//        }
        start()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        Log.e(this::class.simpleName, "Start command")
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val serviceNotification =
            NotificationCompat.Builder(this, Constants.Notifications.NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Fetching weather data...")
                .setContentText("Starting...")
                .setSmallIcon(R.drawable.ic_day_sunny)
                .setOngoing(true)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(SERVICE_ID, serviceNotification.build(), FOREGROUND_SERVICE_TYPE_LOCATION)
        } else {
            startForeground(SERVICE_ID, serviceNotification.build())
        }

        weatherServiceUseCase.checkCurrentLocationAndWeather()
            .onEach {
                Log.e(this::class.simpleName, "Fetch service $it")
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


    }

    private fun onWeatherFetchedSuccessful(weatherItem: WeatherItem) {
        stop()
        showDailyServiceForecastNotification(applicationContext, weatherItem)
    }

    private fun stop() {
        Log.e(this::class.simpleName, "Stop command")
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
    }
}