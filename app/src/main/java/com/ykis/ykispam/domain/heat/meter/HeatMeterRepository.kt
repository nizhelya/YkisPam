package com.ykis.ykispam.domain.heat.meter

import com.ykis.ykispam.data.remote.heat.meter.GetHeatMeterResponse
import com.ykis.ykispam.domain.family.request.BooleanInt
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure

interface HeatMeterRepository {
    fun getHeatMeter(params: BooleanInt): Either<Failure, List<HeatMeterEntity>>
    suspend fun getHeatMeterList(addressId : Int , uid:String) : GetHeatMeterResponse
}