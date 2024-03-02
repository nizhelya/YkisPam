package com.ykis.ykispam.domain.meter.water.reading.request

import com.ykis.ykispam.data.WaterReadingRepositoryImpl
import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.domain.interactor.UseCase
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import javax.inject.Inject

class DeleteCurrentWaterReading @Inject constructor(
    private val waterReadingRepositoryImpl: WaterReadingRepositoryImpl
) : UseCase<GetSimpleResponse, Int>() {

    override suspend fun run(params: Int): Either<Failure, GetSimpleResponse> {
        return waterReadingRepositoryImpl.deleteCurrentWaterReading(params)
    }
}