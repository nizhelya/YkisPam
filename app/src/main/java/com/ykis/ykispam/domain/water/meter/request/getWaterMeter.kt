package com.ykis.ykispam.domain.water.meter.request

import com.ykis.ykispam.data.WaterMeterRepositoryImpl
import com.ykis.ykispam.domain.family.request.BooleanInt
import com.ykis.ykispam.domain.interactor.UseCase
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import com.ykis.ykispam.domain.water.meter.WaterMeterEntity
import javax.inject.Inject

class GetWaterMeter @Inject constructor(
    private val waterMeterRepositoryImpl: WaterMeterRepositoryImpl
) : UseCase<List<WaterMeterEntity>, BooleanInt>() {

    override suspend fun run(params: BooleanInt): Either<Failure, List<WaterMeterEntity>> {
        return waterMeterRepositoryImpl.getWaterMeter(params)
    }
}