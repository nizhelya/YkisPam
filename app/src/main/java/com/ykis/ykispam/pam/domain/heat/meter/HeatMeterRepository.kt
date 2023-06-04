package com.ykis.ykispam.pam.domain.heat.meter

import com.ykis.ykispam.pam.domain.family.request.BooleanInt
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure

interface HeatMeterRepository {
    fun getHeatMeter(params: BooleanInt): Either<Failure, List<HeatMeterEntity>>
}