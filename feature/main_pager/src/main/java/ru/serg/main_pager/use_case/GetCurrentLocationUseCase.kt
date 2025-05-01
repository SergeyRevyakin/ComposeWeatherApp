package ru.serg.main_pager.use_case

import ru.serg.location.LocationService
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject constructor(
    private val locationService: LocationService
) {
    operator fun invoke() = locationService.getLocationUpdate(
        isOneTimeRequest = true,
        updateFrequency = 0
    )
}