package com.ykis.mob.data.remote.water.reading

import com.ykis.mob.data.remote.core.BaseResponse
import com.ykis.mob.domain.meter.water.reading.AddWaterReadingParams

interface WaterReadingRemote {
    suspend fun getWaterReadings(vodomerId: Int, uid:String):GetWaterReadingsResponse
    suspend fun getLastWaterReading(vodomerId: Int , uid:String):GetLastWaterReadingResponse
    suspend fun addWaterReading(params: AddWaterReadingParams):BaseResponse
    suspend fun deleteLastReading(readingId:Int , uid:String):BaseResponse
}