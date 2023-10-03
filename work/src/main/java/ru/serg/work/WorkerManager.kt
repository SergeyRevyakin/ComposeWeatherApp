package ru.serg.work

import android.content.Context
import javax.inject.Inject

class WorkerManager @Inject constructor(
    private val context: Context,
) {
    fun setWeatherWorker(int: Int) {
        WeatherWorker.setupPeriodicWork(context, interval = int.toLong())
    }

    fun isWorkerSet() = WeatherWorker.isWeatherWorkerSet(context)

    suspend fun disableWeatherWorker() = WeatherWorker.cancelPeriodicWork(context)

}