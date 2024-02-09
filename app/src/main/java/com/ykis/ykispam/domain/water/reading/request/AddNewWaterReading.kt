package com.ykis.ykispam.pam.domain.water.reading.request

import com.ykis.ykispam.pam.data.WaterReadingRepositoryImpl
import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.domain.interactor.UseCase
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import javax.inject.Inject

class AddNewWaterReading @Inject constructor(
    private val waterReadingRepositoryImpl: WaterReadingRepositoryImpl
) : UseCase<GetSimpleResponse, AddReadingParams>() {

    override suspend fun run(params: AddReadingParams): Either<Failure, GetSimpleResponse> {
        return waterReadingRepositoryImpl.addNewWaterReading(params)
    }
}

data class AddReadingParams(
    val meterId: Int,
    val newValue: Int,
    val currentValue: Int
)