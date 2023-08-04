package ru.serg.composeweatherapp.worker

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
import com.serg.model.WeatherItem
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.serg.composeweatherapp.data.WorkerUseCase
import ru.serg.composeweatherapp.utils.DateUtils.Companion.getHour
import ru.serg.composeweatherapp.utils.common.NetworkResult
import ru.serg.composeweatherapp.utils.common.showDailyForecastNotification
import ru.serg.composeweatherapp.utils.common.showFetchErrorNotification
import ru.serg.composeweatherapp.utils.common.showNotification
import java.util.concurrent.TimeUnit

@HiltWorker
class WeatherWorker @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted params: WorkerParameters,
    private val workerUseCase: WorkerUseCase,
) : CoroutineWorker(appContext, params) {

    companion object {

        private val uniqueWorkName = WeatherWorker::class.java.simpleName
        private const val workerTag = "weather_worker_tag"

        fun setupPeriodicWork(context: Context, interval: Long) {
            Log.e(this::class.simpleName, "Worker interval $interval")

            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val repeatingWork =
                PeriodicWorkRequestBuilder<WeatherWorker>(interval, TimeUnit.HOURS)
                    .addTag(workerTag)
                    .setInitialDelay(interval, TimeUnit.MINUTES)
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
            WorkManager.getInstance(context).getWorkInfosByTag(workerTag).get()?.let {
                it.isNotEmpty() && !listOf(
                    WorkInfo.State.CANCELLED,
                    WorkInfo.State.BLOCKED,
                    WorkInfo.State.FAILED
                ).contains(it.first().state)
            } ?: false

        suspend fun cancelPeriodicWork(context: Context) {
            Log.e(this::class.simpleName, "Worker cancelled")
            WorkManager.getInstance(context).cancelAllWorkByTag(workerTag).await()
        }
    }

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override suspend fun doWork(): Result {
        return try {

            fetchWeatherForNotification()

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

    private fun fetchWeatherForNotification() {
        workerUseCase.fetchFavouriteCity()
            .onEach { networkResult ->
                Log.e(this::class.simpleName, "Fetch service $networkResult")
                when (networkResult) {
                    is NetworkResult.Success -> {
                        onWeatherFetchedSuccessful(networkResult.data)
                        networkResult.data?.alertMessage?.let {
                            showNotification(applicationContext, "ALERT", it)
                        }
                    }

                    is NetworkResult.Error -> {
                        onError(networkResult.message)
                    }

                    is NetworkResult.Loading -> {}
                }
            }.launchIn(serviceScope)
    }

    private fun onWeatherFetchedSuccessful(weatherItem: WeatherItem?) {
        weatherItem?.let {
            showDailyForecastNotification(applicationContext, weatherItem)
        }
    }

    private fun onError(errorText: String?) {
        showFetchErrorNotification(applicationContext, errorText)
    }
}