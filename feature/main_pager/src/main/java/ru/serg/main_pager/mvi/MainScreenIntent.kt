package ru.serg.main_pager.mvi

import ru.serg.main_pager.PagerScreenError
import ru.serg.main_pager.PagerScreenState
import ru.serg.mvi_core.models.IAction

sealed class MainScreenIntent : IAction {

    data object RefreshScreen : MainScreenIntent()

    data object GetLocationWeather : MainScreenIntent()

    data class PushStateToBackStack(
        val state: PagerScreenState
    ) : MainScreenIntent()

    data class SetLocationPermission(val isGranted: Boolean) : MainScreenIntent()

    data class SetPageNumber(val pageNumber: Int) : MainScreenIntent()

    data class ShowEmptyCitiesScreen(val hasWelcomeDialog: Boolean, val isLoading: Boolean) :
        MainScreenIntent()

    data class SetNetworkAvailability(val isAvailable: Boolean) : MainScreenIntent()

    data class SetError(val error: PagerScreenError) : MainScreenIntent()
}