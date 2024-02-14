package com.ykis.ykispam.domain.service.request


data class ServiceParams(
    val uid:String,
    val addressId:Int,
    val houseId:Int ,
    val service:Byte,
    // TODO: remove total
    val total:Byte,
    val year : String ,
)