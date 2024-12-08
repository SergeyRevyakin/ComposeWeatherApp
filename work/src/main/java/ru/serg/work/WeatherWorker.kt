package ru.serg.work

import android.content.Context
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
import androidx.work.await
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collectLatest
import ru.serg.common.NetworkResult
import ru.serg.common.asResult
import ru.serg.model.WeatherItem
import ru.serg.notifications.showDailyForecastNotification
import ru.serg.notifications.showFetchErrorNotification
import ru.serg.notifications.showNotification
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

@HiltWorker
class WeatherWorker @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted params: WorkerParameters,
    private val workerUseCase: WorkerUseCase,
    private val notificationAvailabilityUseCase: NotificationAvailabilityUseCase
) : CoroutineWorker(appContext, params) {

    companion object {

        private val uniqueWorkName = WeatherWorker::class.java.simpleName
        private const val WORKER_TAG = "weather_worker_tag"

        fun setupPeriodicWork(context: Context, interval: Long) {
            Log.e(this::class.simpleName, "Worker interval $interval")

            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val repeatingWork =
                PeriodicWorkRequestBuilder<WeatherWorker>(interval, TimeUnit.HOURS)
                    .setInitialDelay(30, TimeUnit.SECONDS)
                    .addTag(WORKER_TAG)
                    .setConstraints(constraints)
                    .build()


            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                uniqueWorkName,
                ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
                repeatingWork
            )
            Log.e(this::class.simpleName, "Worker set")
        }

        fun isWeatherWorkerSet(context: Context) =
            WorkManager.getInstance(context).getWorkInfosByTag(WORKER_TAG).get()?.let {
                it.isNotEmpty() && !listOf(
                    WorkInfo.State.CANCELLED,
                    WorkInfo.State.BLOCKED,
                    WorkInfo.State.FAILED
                ).contains(it.first().state)
            } ?: false

        suspend fun cancelPeriodicWork(context: Context) {
            Log.e(this::class.simpleName, "Worker cancelled")
            WorkManager.getInstance(context).cancelAllWorkByTag(WORKER_TAG).await()
        }
    }


    override suspend fun doWork(): Result {
        Log.e(this::class.simpleName, "Worker started")
        return try {

            fetchWeatherForNotification()

            Log.e(this::class.simpleName, "Worker succeed")
            Result.success()

        } catch (e: Exception) {
            notificationAvailabilityUseCase().collectLatest {
                if (it) {
                    showNotification(
                        applicationContext,
                        "Worker failed",
                        e.message + getHour(System.currentTimeMillis())
                    )
                }
            }

            Log.e(this::class.simpleName, "Worker failed: ${e.message}\n ${e.stackTrace}")
            Result.failure()
        }
    }

    private suspend fun fetchWeatherForNotification() {
        workerUseCase()
            .asResult()
            .collectLatest { networkResult ->
                Log.e(this::class.simpleName, "Worker result $networkResult")
                when (networkResult) {
                    is NetworkResult.Success -> {
                        notificationAvailabilityUseCase().collectLatest { isNotificationOn ->
                            if (isNotificationOn) {
                                onWeatherFetchedSuccessful(networkResult.data)
                                networkResult.data.alertMessage?.let {
                                    showNotification(applicationContext, "ALERT", it)
                                }
                            }
                        }
                    }

                    is NetworkResult.Error -> {
                        notificationAvailabilityUseCase().collectLatest { isNotificationOn ->
                            if (isNotificationOn) {
                                onError(networkResult.message)
                            }
                        }
                    }

                    is NetworkResult.Loading -> Unit
                }
            }
    }

    private fun onWeatherFetchedSuccessful(weatherItem: WeatherItem?) {
        weatherItem?.let {
            showDailyForecastNotification(applicationContext, weatherItem)
        }
    }

    private fun onError(errorText: String?) {
        showFetchErrorNotification(applicationContext, errorText)
    }

    private fun getHour(l: Long?): String =
        SimpleDateFormat("HH:mm", Locale.getDefault()).format((l ?: 0L))
}