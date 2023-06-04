package com.ykis.ykispam.pam.data.remote.heat.reading

import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.domain.heat.reading.HeatReadingEntity
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure


interface HeatReadingRemote {
    fun getHeatReadings(
        teplomerId: Int,
        userId: Int,
        token: String
    ): Either<Failure, List<HeatReadingEntity>>

    fun addNewHeatReading(
        teplomerId: Int,
        newValue: Double,
        currentValue: Double,
        userId: Int,
        token: String
    ): Either<Failure, GetSimpleResponse>

    fun deleteCurrentHeatReading(
        pokId: Int,
        userId: Int,
        token: String
    ): Either<Failure, GetSimpleResponse>
}