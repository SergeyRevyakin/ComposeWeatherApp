package ru.serg.composeweatherapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import dev.shreyaspatil.permissionFlow.PermissionFlow
import kotlinx.coroutines.Dispatchers
import ru.serg.composeweatherapp.utils.Constants
import javax.inject.Inject

@HiltAndroidApp
class ComposeWeatherApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(Log.DEBUG)
            .build()

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        PermissionFlow.init(this, Dispatchers.Default)
    }

    private fun createNotificationChannel() {

        val id = Constants.Notifications.NOTIFICATION_CHANNEL_ID
        val name = Constants.Notifications.NOTIFICATION_CHANNEL
        val descriptionText = Constants.Notifications.NOTIFICATION_CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_LOW

        val channel = NotificationChannel(id, name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}