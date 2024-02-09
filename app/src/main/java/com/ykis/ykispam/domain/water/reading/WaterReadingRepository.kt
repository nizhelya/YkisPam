package com.ykis.ykispam.pam.domain.water.reading

import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.domain.family.request.BooleanInt
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import com.ykis.ykispam.pam.domain.water.reading.request.AddReadingParams

interface WaterReadingRepository {
    fun getWaterReading(params: BooleanInt): Either<Failure, List<WaterReadingEntity>>
    fun addNewWaterReading(params: AddReadingParams): Either<Failure, GetSimpleResponse>
    fun deleteCurrentWaterReading(params: Int): Either<Failure, GetSimpleResponse>
}