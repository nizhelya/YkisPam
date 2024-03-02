package com.ykis.ykispam.data.remote.water.reading

import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.domain.meter.water.AddReadingParams
import com.ykis.ykispam.domain.meter.water.reading.WaterReadingEntity
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure

interface WaterReadingRemote {
    fun getWaterReading(
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

    suspend fun getWaterReadings(
        vodomerId: Int,
        uid:String
    ):GetWaterReadingsResponse

    suspend fun getLastWaterReading(vodomerId: Int , uid:String):GetLastWaterReadingResponse
    suspend fun addWaterReading(params:AddReadingParams):GetSimpleResponse
}