package ru.serg.composeweatherapp.worker

import android.annotation.SuppressLint
import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.serg.composeweatherapp.data.LocalRepository
import ru.serg.composeweatherapp.data.RemoteRepository
import ru.serg.composeweatherapp.data.WorkerUseCase
import ru.serg.composeweatherapp.data.room.WeatherUnit
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

@HiltWorker
class WeatherWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val workerUseCase: WorkerUseCase
) : CoroutineWorker(appContext, params) {

    companion object {

        private val uniqueWorkName = WeatherWorker::class.java.simpleName

        fun enqueue(context: Context, force: Boolean = false) {
            val manager = WorkManager.getInstance(context)
            val requestBuilder = OneTimeWorkRequestBuilder<WeatherWorker>()
                .setInitialDelay(1, TimeUnit.MINUTES)
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

    }

    @SuppressLint("MissingPermission")
    override suspend fun doWork(): Result {
        return try {

            localRepository.saveInDatabase(WeatherUnit(name = "WORKER_STARTED ${LocalDateTime.now()}"))

            localRepository.getLastSavedLocation().let {
                workerUseCase.fetchWeather(it.latitude, it.longitude)
            }

            //TODO Get current location

            Result.success()
        } catch (e: Exception) {
            withContext(Dispatchers.IO) {
                localRepository.saveInDatabase(WeatherUnit(name = "FAILED"))
            }
            Result.failure()
        }
    }
}