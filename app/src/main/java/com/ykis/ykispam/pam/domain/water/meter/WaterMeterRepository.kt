package com.ykis.ykispam.pam.domain.water.meter


import com.ykis.ykispam.pam.domain.family.request.BooleanInt
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure

interface WaterMeterRepository {
    fun getWaterMeter(params: BooleanInt): Either<Failure, List<WaterMeterEntity>>
}