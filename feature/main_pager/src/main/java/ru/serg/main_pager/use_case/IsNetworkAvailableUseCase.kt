package ru.serg.main_pager.use_case

import ru.serg.common.NetworkStatusSource
import javax.inject.Inject

class IsNetworkAvailableUseCase @Inject constructor(
    private val networkStatusSource: NetworkStatusSource
) {
    operator fun invoke() =
        networkStatusSource.isNetworkAvailableFlow()
}