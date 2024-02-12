package com.ykis.ykispam.domain.heat.reading.request

import com.ykis.ykispam.data.HeatReadingRepositoryImpl
import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.domain.interactor.UseCase
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import javax.inject.Inject

class DeleteCurrentHeatReading @Inject constructor(
    private val heatReadingRepositoryImpl: HeatReadingRepositoryImpl
) : UseCase<GetSimpleResponse, Int>() {

    override suspend fun run(params: Int): Either<Failure, GetSimpleResponse> {
        return heatReadingRepositoryImpl.deleteCurrentHeatReading(params)
    }
}