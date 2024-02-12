package com.ykis.ykispam.data.remote.water.reading

import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import com.ykis.ykispam.domain.water.reading.WaterReadingEntity

interface WaterReadingRemote {
    fun getWaterReadings(
        vodomerId: Int,
        uid: String
    ): Either<Failure, List<WaterReadingEntity>>

    fun addNewWaterReading(
        vodomerId: Int,
        newValue: Int,
        currentValue: Int,
        uid: String
    ): Either<Failure, GetSimpleResponse>

    fun deleteCurrentWaterReading(
        pokId: Int,
        uid: String
    ): Either<Failure, GetSimpleResponse>
}