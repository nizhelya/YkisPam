package com.ykis.mob.data.remote.core

open class BaseResponse(
    var success: Int,
    val message: String,
    val addressId:Int = 0
)