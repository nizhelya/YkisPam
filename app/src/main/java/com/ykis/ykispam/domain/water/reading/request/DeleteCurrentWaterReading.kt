package com.ykis.ykispam.pam.domain.water.reading.request

import com.ykis.ykispam.pam.data.WaterReadingRepositoryImpl
import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.domain.interactor.UseCase
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import javax.inject.Inject

class DeleteCurrentWaterReading @Inject constructor(
    private val waterReadingRepositoryImpl: WaterReadingRepositoryImpl
) : UseCase<GetSimpleResponse, Int>() {

    override suspend fun run(params: Int): Either<Failure, GetSimpleResponse> {
        return waterReadingRepositoryImpl.deleteCurrentWaterReading(params)
    }
}