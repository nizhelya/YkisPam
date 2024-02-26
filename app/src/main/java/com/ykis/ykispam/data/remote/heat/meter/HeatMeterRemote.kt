package com.ykis.ykispam.data.remote.heat.meter

import com.ykis.ykispam.domain.heat.meter.HeatMeterEntity
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure

interface HeatMeterRemote {
    fun getHeatMeter(
        addressId: Int,
        uid: String
    ): Either<Failure, List<HeatMeterEntity>>

    suspend fun getHeatMeterList(addressId: Int,uid: String) : GetHeatMeterResponse
}