package ru.serg.composeweatherapp.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ru.serg.composeweatherapp.data.LocalRepository
import ru.serg.composeweatherapp.data.RemoteRepository
import ru.serg.composeweatherapp.data.room.WeatherUnit
import java.util.*

@HiltWorker
class WeatherWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
):CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        localRepository.saveInDatabase(WeatherUnit(name = Calendar.getInstance().get(Calendar.MINUTE).toString()))
        println("<-----WOHOOO----->")
        return Result.success()
    }
}