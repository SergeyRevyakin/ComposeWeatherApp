package ru.serg.composeweatherapp.worker

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.ktor.util.date.getTimeMillis
import ru.serg.composeweatherapp.data.WorkerUseCase
import ru.serg.composeweatherapp.data.data_source.LocalDataSource
import ru.serg.composeweatherapp.data.data_source.RemoteDataSource
import ru.serg.composeweatherapp.service.FetchWeatherService
import ru.serg.composeweatherapp.utils.DateUtils.Companion.getHour
import ru.serg.composeweatherapp.utils.Ext.showNotification
import java.util.concurrent.TimeUnit

@HiltWorker
class WeatherWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val workerUseCase: WorkerUseCase
) : CoroutineWorker(appContext, params) {

    companion object {

        private val uniqueWorkName = WeatherWorker::class.java.simpleName
        private const val workerTag = "weather_worker_tag"

        fun enqueue(context: Context, force: Boolean = false) {
            val manager = WorkManager.getInstance(context)
            val requestBuilder = OneTimeWorkRequestBuilder<WeatherWorker>()
                .setInitialDelay(15, TimeUnit.MINUTES)
            val workPolicy = if (force) ExistingWorkPolicy.REPLACE
            else ExistingWorkPolicy.KEEP

            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .build()

            manager.enqueueUniqueWork(
                uniqueWorkName,
                workPolicy,
                requestBuilder.setConstraints(constraints).build()
            )
        }

        fun setupPeriodicWork(context: Context) {

            if (WorkManager.getInstance(context).getWorkInfosByTag(workerTag).get().isEmpty()) {

                val constraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()

                val repeatingWork =
                    PeriodicWorkRequestBuilder<WeatherWorker>(15, TimeUnit.MINUTES)
                        .addTag(workerTag)
                        .setInitialDelay(3, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .build()


                WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                    uniqueWorkName,
                    ExistingPeriodicWorkPolicy.KEEP,
                    repeatingWork
                )
            }
        }

    }

    override suspend fun doWork(): Result {
        return try {
            Intent(applicationContext, FetchWeatherService::class.java).apply {
                action = FetchWeatherService.START_ACTION
                applicationContext.startService(this)
            }
            Result.success()

        } catch (e: Exception) {
            showNotification(
                applicationContext,
                "Worker failed",
                e.message + getHour(getTimeMillis())
            )
            Log.e(this::class.simpleName, "Worker failed: ${e.message}\n ${e.stackTrace}" )
            Result.failure()
        }
    }
}