package com.ykis.ykispam.pam.data.remote.water.reading

import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import com.ykis.ykispam.pam.domain.water.reading.WaterReadingEntity

interface WaterReadingRemote {
    fun getWaterReadings(
        vodomerId: Int,
        userId: Int,
        token: String
    ): Either<Failure, List<WaterReadingEntity>>

    fun addNewWaterReading(
        vodomerId: Int,
        newValue: Int,
        currentValue: Int,
        userId: Int,
        token: String
    ): Either<Failure, GetSimpleResponse>

    fun deleteCurrentWaterReading(
        pokId: Int,
        userId: Int,
        token: String
    ): Either<Failure, GetSimpleResponse>
}