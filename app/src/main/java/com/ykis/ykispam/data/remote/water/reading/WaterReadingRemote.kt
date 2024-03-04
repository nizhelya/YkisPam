package com.ykis.ykispam.data.remote.water.reading

import com.ykis.ykispam.data.remote.core.BaseResponse
import com.ykis.ykispam.domain.meter.water.reading.AddWaterReadingParams

interface WaterReadingRemote {
    suspend fun getWaterReadings(vodomerId: Int, uid:String):GetWaterReadingsResponse
    suspend fun getLastWaterReading(vodomerId: Int , uid:String):GetLastWaterReadingResponse
    suspend fun addWaterReading(params: AddWaterReadingParams):BaseResponse
    suspend fun deleteLastReading(readingId:Int , uid:String):BaseResponse
}