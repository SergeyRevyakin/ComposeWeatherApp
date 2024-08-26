@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package ru.serg.widgets.worker

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.serg.local.LocalDataSource
import javax.inject.Inject

class UpdateWorkerUseCase @Inject constructor(
    private val localDataSource: LocalDataSource,
) {
    suspend operator fun invoke() = localDataSource.cleanOutdatedWeather()
}
