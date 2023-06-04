package com.ykis.ykispam.pam.domain.heat.reading

import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.domain.family.request.BooleanInt
import com.ykis.ykispam.pam.domain.heat.reading.request.AddHeatReadingParams
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure

interface HeatReadingRepository {
    fun getHeatReading(params: BooleanInt): Either<Failure, List<HeatReadingEntity>>
    fun addNewHeatReading(params: AddHeatReadingParams): Either<Failure, GetSimpleResponse>
    fun deleteCurrentHeatReading(params: Int): Either<Failure, GetSimpleResponse>

}