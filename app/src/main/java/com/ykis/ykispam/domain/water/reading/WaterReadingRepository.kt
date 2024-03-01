package com.ykis.ykispam.domain.water.reading

import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.data.remote.water.reading.GetLastWaterReadingResponse
import com.ykis.ykispam.data.remote.water.reading.GetWaterReadingsResponse
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import com.ykis.ykispam.domain.water.reading.request.AddReadingParams

interface WaterReadingRepository {
    fun addNewWaterReading(params: AddReadingParams): Either<Failure, GetSimpleResponse>
    fun deleteCurrentWaterReading(params: Int): Either<Failure, GetSimpleResponse>

    suspend fun getWaterReadings(vodomerId:Int , uid:String):GetWaterReadingsResponse
    suspend fun getLastWaterReading(vodomerId: Int , uid: String):GetLastWaterReadingResponse
}