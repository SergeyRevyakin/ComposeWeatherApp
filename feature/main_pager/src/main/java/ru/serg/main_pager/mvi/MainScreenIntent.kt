package ru.serg.main_pager.mvi

sealed class MainScreenIntent {
    data object InitScreen : MainScreenIntent()

    data object RefreshScreen : MainScreenIntent()

    data class SetLocationPermission(val isGranted: Boolean) : MainScreenIntent()

    data class SetPageNumber(val pageNumber: Int) : MainScreenIntent()

    data object TurnOffWelcomeDialog : MainScreenIntent()

    data class SetNetworkAvailability(val isAvailable: Boolean) : MainScreenIntent()
}