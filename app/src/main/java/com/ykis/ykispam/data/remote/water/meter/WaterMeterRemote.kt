package com.ykis.ykispam.data.remote.water.meter

interface WaterMeterRemote {
    suspend fun getWaterMeterList(addressId: Int, uid:String):GetWaterMeterResponse
}