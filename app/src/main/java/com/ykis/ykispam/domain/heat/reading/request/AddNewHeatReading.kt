package com.ykis.ykispam.domain.heat.reading.request

import com.ykis.ykispam.data.HeatReadingRepositoryImpl
import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.domain.interactor.UseCase
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import javax.inject.Inject

class AddNewHeatReading @Inject constructor(
    private val heatReadingRepositoryImpl: HeatReadingRepositoryImpl
) : UseCase<GetSimpleResponse, AddHeatReadingParams>() {

    override suspend fun run(params: AddHeatReadingParams): Either<Failure, GetSimpleResponse> {
        return heatReadingRepositoryImpl.addNewHeatReading(params)
    }
}

data class AddHeatReadingParams(
    val meterId: Int,
    val newValue: Double,
    val currentValue: Double
)