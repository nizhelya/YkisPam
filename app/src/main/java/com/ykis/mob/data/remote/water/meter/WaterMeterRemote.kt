package com.ykis.mob.data.remote.water.meter

interface WaterMeterRemote {
    suspend fun getWaterMeterList(addressId: Int, uid:String):GetWaterMeterResponse
}