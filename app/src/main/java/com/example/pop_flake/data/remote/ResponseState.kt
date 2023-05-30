package com.example.pop_flake.data.remote

sealed class ResponseState<out T> {
    data class Success<out T>(val data: T) : ResponseState<T>()
    data class Error(val message: String) : ResponseState<Nothing>()
    object Loading : ResponseState<Nothing>()
}

