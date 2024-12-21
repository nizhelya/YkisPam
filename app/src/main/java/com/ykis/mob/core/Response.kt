package com.ykis.mob.core

import com.ykis.mob.R


sealed class Resource<T>(val data: T? = null, val message: String? = null , val resourceMessage: Int = R.string.generic_error) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(  message: String? = null,resourceMessage : Int = R.string.generic_error, data: T? = null) : Resource<T>(data, message , resourceMessage)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}