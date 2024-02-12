package com.ykis.ykispam.domain.heat.meter.request

import com.ykis.ykispam.data.HeatMeterRepositoryImpl
import com.ykis.ykispam.domain.family.request.BooleanInt
import com.ykis.ykispam.domain.heat.meter.HeatMeterEntity
import com.ykis.ykispam.domain.interactor.UseCase
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import javax.inject.Inject

class GetHeatMeter @Inject constructor(
    private val heatMeterRepositoryImpl: HeatMeterRepositoryImpl
) : UseCase<List<HeatMeterEntity>, BooleanInt>() {

    override suspend fun run(params: BooleanInt): Either<Failure, List<HeatMeterEntity>> {
        return heatMeterRepositoryImpl.getHeatMeter(params)
    }
}