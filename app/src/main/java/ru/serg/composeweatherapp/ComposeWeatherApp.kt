package ru.serg.composeweatherapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.serg.composeweatherapp.worker.WeatherWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class ComposeWeatherApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(Log.DEBUG)
            .build()

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
//        doWork()
    }



    private fun createNotificationChannel() {

        val name = "NOTIFICATION_CHANNEL"
        val descriptionText = "NOTIFICATION_CHANNEL_DESC"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("123", name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}