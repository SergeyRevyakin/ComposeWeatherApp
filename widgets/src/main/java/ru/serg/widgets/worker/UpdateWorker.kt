package ru.serg.widgets.worker

import android.content.Context
import android.util.Log
import androidx.glance.appwidget.updateAll
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import ru.serg.widgets.WeatherWidget
import java.util.concurrent.TimeUnit

@HiltWorker
class UpdateWorker @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted params: WorkerParameters,
    private val updateWorkerUseCase: UpdateWorkerUseCase,
) : CoroutineWorker(appContext, params) {

    companion object {

        private val uniqueWorkName = UpdateWorker::class.java.simpleName
        private const val UPDATER_WORKER_TAG = "updater_worker_tag"
        private const val TAG = "UPDATER_WORKER_TAG"

        fun setupPeriodicWork(context: Context) {

            val repeatingWork =
                PeriodicWorkRequestBuilder<UpdateWorker>(15, TimeUnit.MINUTES)
                    .addTag(UPDATER_WORKER_TAG)
                    .build()


            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                uniqueWorkName,
                ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
                repeatingWork
            )
            Log.e(TAG, "Worker set")
        }

    }

    override suspend fun doWork(): Result {
        updateWorkerUseCase()
        delay(1000)
        Log.e(TAG, "-- doWork upd")
        WeatherWidget().updateAll(appContext)
        return Result.success()
    }

}