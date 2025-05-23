package com.senacgrupovinteecinco.pet_connect.model

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure<out T>(val error: Throwable, val data: T? = null) : Result<T>()
}