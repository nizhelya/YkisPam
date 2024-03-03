package com.ykis.ykispam.domain.meter.water.reading

import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.data.remote.core.BaseResponse
import com.ykis.ykispam.data.remote.water.reading.GetLastWaterReadingResponse
import com.ykis.ykispam.data.remote.water.reading.GetWaterReadingsResponse
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure

interface WaterReadingRepository {
    fun addNewWaterReading(params: AddWaterReadingParams): Either<Failure, GetSimpleResponse>

    suspend fun getWaterReadings(vodomerId:Int , uid:String):GetWaterReadingsResponse
    suspend fun getLastWaterReading(vodomerId: Int , uid: String):GetLastWaterReadingResponse
    suspend fun addWaterReading(params: AddWaterReadingParams):BaseResponse
    suspend fun deleteLastWaterReading(readingId:Int , uid:String):BaseResponse
}