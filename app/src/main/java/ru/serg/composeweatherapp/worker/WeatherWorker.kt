package ru.serg.composeweatherapp.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.serg.composeweatherapp.data.WorkerUseCase
import ru.serg.composeweatherapp.data.data_source.LocalDataSource
import ru.serg.composeweatherapp.data.data_source.RemoteDataSource
import ru.serg.composeweatherapp.data.room.WeatherUnit
import ru.serg.composeweatherapp.utils.Ext.showNotification
import java.time.LocalDateTime
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

            // Replace any enqueued work and expedite the request


            manager.enqueueUniqueWork(
                uniqueWorkName,
                workPolicy,
                requestBuilder.setConstraints(constraints).build()
            )
        }

        fun setupPeriodicWork(context: Context) {

            if (WorkManager.getInstance(context).getWorkInfosByTag("TAG").get().isEmpty()) {

                val constraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()

                val repeatingWork =
                    PeriodicWorkRequestBuilder<WeatherWorker>(15, TimeUnit.MINUTES)
                        .addTag("TAG")
                        .setInitialDelay(3, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .build()


                WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                    "W",
                    ExistingPeriodicWorkPolicy.KEEP,
                    repeatingWork
                )
            }
        }

    }

    override suspend fun doWork(): Result {
        return try {
//
////            CoroutineScope(Dispatchers.IO).launch {
//                localRepository.saveInDatabase(WeatherUnit(name = "WORKER_STARTED ${LocalDateTime.now()}"))
//
////                localRepository.getLastSavedLocation().let {
//                    workerUseCase.fetchWeather().collectLatest { temp ->
//                        showNotification(
//                            applicationContext,
//                            "Success",
//                            "${LocalDateTime.now()} Temp: $temp"
//                        )
//                        Result.success()
//                    }
////                    Result.success()
////                }
////            }

            //TODO Get current location
            showNotification(
                applicationContext,
                "Success",
                "${LocalDateTime.now()}"
            )
            Result.success()
        } catch (e: Exception) {
            withContext(Dispatchers.IO) {
                localDataSource.saveInDatabase(WeatherUnit(name = "FAILED"))
            }
            Result.failure()
        }
    }
}