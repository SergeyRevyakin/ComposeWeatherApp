package ru.serg.widgets.worker

import android.content.Context
import android.util.Log
import androidx.glance.appwidget.updateAll
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.serg.common.NetworkResult
import ru.serg.common.asResult
import ru.serg.widgets.WeatherWidget

@HiltWorker
class WidgetWorker @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted params: WorkerParameters,
    private val workerUseCase: WorkerUseCase,
) : CoroutineWorker(appContext, params) {

    companion object {

        private val uniqueWorkName = WidgetWorker::class.java.simpleName

        fun work(context: Context) {
            val request = OneTimeWorkRequest.from(WidgetWorker::class.java)
            WorkManager.getInstance(context)
                .beginUniqueWork(uniqueWorkName, ExistingWorkPolicy.REPLACE, request).enqueue()
        }
    }

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override suspend fun doWork(): Result {
        return try {
            fetchWeather()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun fetchWeather() {
        serviceScope.launch {
            workerUseCase.fetchData()
                .asResult()
                .collectLatest {
                    if (it is NetworkResult.Success) {
                        WeatherWidget().updateAll(applicationContext)
                        Log.e(this::class.simpleName, "Widget updated")
                    }
                }

        }
    }

}