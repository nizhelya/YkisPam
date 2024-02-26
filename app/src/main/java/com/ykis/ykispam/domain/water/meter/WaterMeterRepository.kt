package com.ykis.ykispam.domain.water.meter


import com.ykis.ykispam.data.remote.water.meter.GetWaterMeterResponse
import com.ykis.ykispam.domain.family.request.BooleanInt
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure

interface WaterMeterRepository {
    fun getWaterMeter(params: BooleanInt): Either<Failure, List<WaterMeterEntity>>
    suspend fun getWaterMeterList(addressId: Int , uid : String): GetWaterMeterResponse
}