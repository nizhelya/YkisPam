package com.ykis.ykispam.pam.domain.heat.reading.request

import com.ykis.ykispam.pam.data.HeatReadingRepositoryImpl
import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.domain.interactor.UseCase
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
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