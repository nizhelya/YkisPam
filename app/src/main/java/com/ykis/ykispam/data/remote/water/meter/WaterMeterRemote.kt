package com.ykis.ykispam.data.remote.water.meter

import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import com.ykis.ykispam.domain.water.meter.WaterMeterEntity

interface WaterMeterRemote {
    fun getWaterMeter(
        addressId: Int,
        uid: String
    ): Either<Failure, List<WaterMeterEntity>>

    suspend fun getWaterMeterList(addressId: Int, uid:String):GetWaterMeterResponse
}