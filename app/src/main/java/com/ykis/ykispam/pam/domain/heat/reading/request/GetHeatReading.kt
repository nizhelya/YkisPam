package com.ykis.ykispam.pam.domain.heat.reading.request

import com.ykis.ykispam.pam.data.HeatReadingRepositoryImpl
import com.ykis.ykispam.pam.domain.family.request.BooleanInt
import com.ykis.ykispam.pam.domain.heat.reading.HeatReadingEntity
import com.ykis.ykispam.pam.domain.interactor.UseCase
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import javax.inject.Inject

class GetHeatReading @Inject constructor(
    private val heatReadingRepositoryImpl: HeatReadingRepositoryImpl
) : UseCase<List<HeatReadingEntity>, BooleanInt>() {

    override suspend fun run(params: BooleanInt): Either<Failure, List<HeatReadingEntity>> {
        return heatReadingRepositoryImpl.getHeatReading(params)
    }
}