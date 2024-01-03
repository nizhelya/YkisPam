package com.ykis.ykispam.pam.domain.service.request


data class ServiceParams(
    val uid:String,
    val addressId: Int,
    val houseId: Int,
    val service: Byte,
    val total: Byte,
    val qty: Byte,
    var needFetch: Boolean
)