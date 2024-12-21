package com.ykis.mob.domain.meter.water.reading

import com.ykis.mob.data.remote.core.BaseResponse
import com.ykis.mob.data.remote.water.reading.GetLastWaterReadingResponse
import com.ykis.mob.data.remote.water.reading.GetWaterReadingsResponse

interface WaterReadingRepository {
    suspend fun getWaterReadings(vodomerId:Int , uid:String):GetWaterReadingsResponse
    suspend fun getLastWaterReading(vodomerId: Int , uid: String):GetLastWaterReadingResponse
    suspend fun addWaterReading(params: AddWaterReadingParams):BaseResponse
    suspend fun deleteLastWaterReading(readingId:Int , uid:String):BaseResponse
}