@file:OptIn(ExperimentalCoroutinesApi::class)

package ru.serg.work

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.serg.datastore.DataStoreDataSource
import javax.inject.Inject

class NotificationAvailabilityUseCase @Inject constructor(
    private val dataStoreDataSource: DataStoreDataSource
) {
    operator fun invoke() =
        dataStoreDataSource.isUserNotificationOn
}
