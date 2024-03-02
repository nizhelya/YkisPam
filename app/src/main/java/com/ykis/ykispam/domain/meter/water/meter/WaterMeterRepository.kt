package com.ykis.ykispam.domain.meter.water.meter


import com.ykis.ykispam.data.remote.water.meter.GetWaterMeterResponse

interface WaterMeterRepository {
    suspend fun getWaterMeterList(addressId: Int , uid : String): GetWaterMeterResponse
}