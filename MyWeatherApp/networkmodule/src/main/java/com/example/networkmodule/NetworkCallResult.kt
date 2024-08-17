package com.example.networkmodule

sealed class NetworkCallResult<out T> {
    data class Success<T: Any>(val data: T): NetworkCallResult<T>()
    data class Error<T: Any>(val code: Int, val message: String): NetworkCallResult<T>()
    data class Exception<T: Any>(val message: String?, val e: Throwable): NetworkCallResult<T>()
}