package ru.serg.composeweatherapp.utils

import android.content.Context
import ru.serg.composeweatherapp.worker.WeatherWorker
import javax.inject.Inject

class WorkerManager @Inject constructor(
    private val context: Context,
) {
    fun setWeatherWorker(int: Int) {
        WeatherWorker.setupPeriodicWork(context, interval = int.toLong())
    }

    fun isWorkerSet() = WeatherWorker.isWeatherWorkerSet(context)

    fun disableWeatherWorker() = WeatherWorker.cancelPeriodicWork(context)

}