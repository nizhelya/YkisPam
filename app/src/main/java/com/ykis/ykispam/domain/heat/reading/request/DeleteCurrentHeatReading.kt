package com.ykis.ykispam.pam.domain.heat.reading.request

import com.ykis.ykispam.pam.data.HeatReadingRepositoryImpl
import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.domain.interactor.UseCase
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import javax.inject.Inject

class DeleteCurrentHeatReading @Inject constructor(
    private val heatReadingRepositoryImpl: HeatReadingRepositoryImpl
) : UseCase<GetSimpleResponse, Int>() {

    override suspend fun run(params: Int): Either<Failure, GetSimpleResponse> {
        return heatReadingRepositoryImpl.deleteCurrentHeatReading(params)
    }
}