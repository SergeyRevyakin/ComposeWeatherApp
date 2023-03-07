package ru.serg.composeweatherapp.worker

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.ktor.util.date.getTimeMillis
import ru.serg.composeweatherapp.service.FetchWeatherService
import ru.serg.composeweatherapp.utils.DateUtils.Companion.getHour
import ru.serg.composeweatherapp.utils.showNotification
import java.util.concurrent.TimeUnit

@HiltWorker
class WeatherWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
) : CoroutineWorker(appContext, params) {

    companion object {

        private val uniqueWorkName = WeatherWorker::class.java.simpleName
        private const val workerTag = "weather_worker_tag"

        fun setupPeriodicWork(context: Context) {

            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val repeatingWork =
                PeriodicWorkRequestBuilder<WeatherWorker>(15, TimeUnit.MINUTES)
                    .addTag(workerTag)
                    .setInitialDelay(1, TimeUnit.MINUTES)
                    .setConstraints(constraints)
                    .build()


            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                uniqueWorkName,
                ExistingPeriodicWorkPolicy.KEEP,
                repeatingWork
            )
            Log.e(this::class.simpleName, "Worker set")
        }

        fun isWeatherWorkerSet(context: Context) =
            WorkManager.getInstance(context).getWorkInfosByTag(workerTag).get()
                .first().state != WorkInfo.State.CANCELLED

        fun cancelPeriodicWork(context: Context) {
            Log.e(this::class.simpleName, "Worker cancelled")
            WorkManager.getInstance(context).cancelAllWork()
        }
    }

    override suspend fun doWork(): Result {
        return try {
            Intent(applicationContext, FetchWeatherService::class.java).apply {
                action = FetchWeatherService.START_ACTION
                applicationContext.startService(this)
            }
            Log.e(this::class.simpleName, "Worker succeed")
            Result.success()

        } catch (e: Exception) {
            showNotification(
                applicationContext,
                "Worker failed",
                e.message + getHour(getTimeMillis())
            )
            Log.e(this::class.simpleName, "Worker failed: ${e.message}\n ${e.stackTrace}")
            Result.failure()
        }
    }
}