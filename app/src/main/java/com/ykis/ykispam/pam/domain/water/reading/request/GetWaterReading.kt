package com.ykis.ykispam.pam.domain.water.reading.request


import com.ykis.ykispam.pam.data.WaterReadingRepositoryImpl
import com.ykis.ykispam.pam.domain.family.request.BooleanInt
import com.ykis.ykispam.pam.domain.interactor.UseCase
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import com.ykis.ykispam.pam.domain.water.reading.WaterReadingEntity
import javax.inject.Inject

class GetWaterReading @Inject constructor(
    private val waterReadingRepositoryImpl: WaterReadingRepositoryImpl
) : UseCase<List<WaterReadingEntity>, BooleanInt>() {

    override suspend fun run(params: BooleanInt): Either<Failure, List<WaterReadingEntity>> {
        return waterReadingRepositoryImpl.getWaterReading(params)
    }
}