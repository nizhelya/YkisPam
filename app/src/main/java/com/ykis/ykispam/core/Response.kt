package com.ykis.ykispam.core

import com.ykis.ykispam.R

sealed class Response<out T> {
    data object Loading: Response<Nothing>()

    data class Success<out T>(
        val data: T?
    ): Response<T>()

    data class Failure(
        val e: Exception
    ): Response<Nothing>()
}

sealed class Resource<T>(val data: T? = null, val message: String? = null , val resourceMessage: Int = R.string.generic_error) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(  message: String? = null,resourceMessage : Int = R.string.generic_error, data: T? = null) : Resource<T>(data, message , resourceMessage)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}