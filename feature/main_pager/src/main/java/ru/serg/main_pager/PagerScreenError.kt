package ru.serg.main_pager

import androidx.compose.runtime.Stable

@Stable
sealed class PagerScreenError {
    data class NetworkError(val message: String, val throwable: Throwable) : PagerScreenError()
}