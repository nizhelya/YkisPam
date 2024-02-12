package com.ykis.ykispam.domain.heat.meter

import com.ykis.ykispam.domain.family.request.BooleanInt
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure

interface HeatMeterRepository {
    fun getHeatMeter(params: BooleanInt): Either<Failure, List<HeatMeterEntity>>
}