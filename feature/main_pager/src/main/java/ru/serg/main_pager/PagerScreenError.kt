package ru.serg.main_pager

import androidx.compose.runtime.Stable

@Stable
sealed class PagerScreenError() {
    data class NetworkError(val throwable: Throwable) :
        PagerScreenError()

    data object GPSError : PagerScreenError()
}