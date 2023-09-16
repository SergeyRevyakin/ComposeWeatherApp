package ru.serg.service

sealed class ServiceFetchingResult<out T>{
    data class Loading(val message:String): ServiceFetchingResult<Nothing>()
    data class Success<T>(val data: T): ServiceFetchingResult<T>()
    data class Error(val message: String): ServiceFetchingResult<Nothing>()
}
