package com.example.myweatherapp

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val code:Any, val message: String) : UiState<Nothing>()
}