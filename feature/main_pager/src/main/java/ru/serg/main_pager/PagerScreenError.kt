package ru.serg.main_pager

import androidx.compose.runtime.Stable

@Stable
sealed class PagerScreenError(open val message: String) {
    data class NetworkError(override val message: String, val throwable: Throwable) :
        PagerScreenError(message)

    data class GPSError(override val message: String) : PagerScreenError(message)
}