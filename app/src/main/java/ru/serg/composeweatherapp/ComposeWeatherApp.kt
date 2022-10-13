package ru.serg.composeweatherapp

import android.app.Application
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
        doWork()
    }

    private fun doWork() {
        applicationScope.launch {
            setupWorker()
        }
    }

    private fun setupWorker() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val repeatingWork = PeriodicWorkRequest.Builder(WeatherWorker::class.java, 15, TimeUnit.MINUTES)
            .addTag("TAG")
            .setInitialDelay(2, TimeUnit.MINUTES)
            .setConstraints(constraints)
//            PeriodicWorkRequestBuilder<WeatherWorker>(1, TimeUnit.MINUTES)
            .build()


        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "W",
            ExistingPeriodicWorkPolicy.REPLACE,
            repeatingWork
        )
    }
}