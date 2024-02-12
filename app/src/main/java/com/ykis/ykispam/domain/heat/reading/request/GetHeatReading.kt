package com.ykis.ykispam.domain.heat.reading.request

import com.ykis.ykispam.data.HeatReadingRepositoryImpl
import com.ykis.ykispam.domain.family.request.BooleanInt
import com.ykis.ykispam.domain.heat.reading.HeatReadingEntity
import com.ykis.ykispam.domain.interactor.UseCase
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import javax.inject.Inject

class GetHeatReading @Inject constructor(
    private val heatReadingRepositoryImpl: HeatReadingRepositoryImpl
) : UseCase<List<HeatReadingEntity>, BooleanInt>() {

    override suspend fun run(params: BooleanInt): Either<Failure, List<HeatReadingEntity>> {
        return heatReadingRepositoryImpl.getHeatReading(params)
    }
}