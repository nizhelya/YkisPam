package com.ykis.ykispam.pam.data.remote.heat.meter

import com.ykis.ykispam.pam.domain.heat.meter.HeatMeterEntity
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure

interface HeatMeterRemote {
    fun getHeatMeter(
        addressId: Int,
        userId: Int,
        token: String
    ): Either<Failure, List<HeatMeterEntity>>
}