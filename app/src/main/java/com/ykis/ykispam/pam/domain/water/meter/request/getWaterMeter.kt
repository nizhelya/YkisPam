package com.ykis.ykispam.pam.domain.water.meter.request

import com.ykis.ykispam.pam.data.WaterMeterRepositoryImpl
import com.ykis.ykispam.pam.domain.family.request.BooleanInt
import com.ykis.ykispam.pam.domain.interactor.UseCase
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import com.ykis.ykispam.pam.domain.water.meter.WaterMeterEntity
import javax.inject.Inject

class GetWaterMeter @Inject constructor(
    private val waterMeterRepositoryImpl: WaterMeterRepositoryImpl
) : UseCase<List<WaterMeterEntity>, BooleanInt>() {

    override suspend fun run(params: BooleanInt): Either<Failure, List<WaterMeterEntity>> {
        return waterMeterRepositoryImpl.getWaterMeter(params)
    }
}