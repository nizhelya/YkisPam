package com.ykis.mob.domain.meter.water.meter


import com.ykis.mob.data.remote.water.meter.GetWaterMeterResponse

interface WaterMeterRepository {
    suspend fun getWaterMeterList(addressId: Int , uid : String): GetWaterMeterResponse
}