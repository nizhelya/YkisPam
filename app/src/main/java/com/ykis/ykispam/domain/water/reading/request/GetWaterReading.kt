package com.ykis.ykispam.domain.water.reading.request


import com.ykis.ykispam.data.WaterReadingRepositoryImpl
import com.ykis.ykispam.domain.family.request.BooleanInt
import com.ykis.ykispam.domain.interactor.UseCase
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import com.ykis.ykispam.domain.water.reading.WaterReadingEntity
import javax.inject.Inject

class GetWaterReading @Inject constructor(
    private val waterReadingRepositoryImpl: WaterReadingRepositoryImpl
) : UseCase<List<WaterReadingEntity>, BooleanInt>() {

    override suspend fun run(params: BooleanInt): Either<Failure, List<WaterReadingEntity>> {
        return waterReadingRepositoryImpl.getWaterReading(params)
    }
}