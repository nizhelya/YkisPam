package com.ykis.ykispam.domain.service.request


data class ServiceParams(
    val uid:String,
    val addressId:Int,
    val houseId:Int ,
    val service:Byte,
    val total:Byte,
    val year : String ,
    var needFetch:Boolean
)