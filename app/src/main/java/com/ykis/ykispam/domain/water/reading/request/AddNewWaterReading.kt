package com.ykis.ykispam.domain.water.reading.request

import com.ykis.ykispam.data.WaterReadingRepositoryImpl
import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.domain.interactor.UseCase
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
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