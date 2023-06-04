package com.ykis.ykispam.pam.data.remote.water.meter

import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import com.ykis.ykispam.pam.domain.water.meter.WaterMeterEntity

interface WaterMeterRemote {
    fun getWaterMeter(
        addressId: Int,
        userId: Int,
        token: String
    ): Either<Failure, List<WaterMeterEntity>>
}