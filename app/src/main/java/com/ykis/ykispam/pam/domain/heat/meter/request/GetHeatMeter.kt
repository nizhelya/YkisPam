package com.ykis.ykispam.pam.domain.heat.meter.request

import com.ykis.ykispam.pam.data.HeatMeterRepositoryImpl
import com.ykis.ykispam.pam.domain.family.request.BooleanInt
import com.ykis.ykispam.pam.domain.heat.meter.HeatMeterEntity
import com.ykis.ykispam.pam.domain.interactor.UseCase
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import javax.inject.Inject

class GetHeatMeter @Inject constructor(
    private val heatMeterRepositoryImpl: HeatMeterRepositoryImpl
) : UseCase<List<HeatMeterEntity>, BooleanInt>() {

    override suspend fun run(params: BooleanInt): Either<Failure, List<HeatMeterEntity>> {
        return heatMeterRepositoryImpl.getHeatMeter(params)
    }
}