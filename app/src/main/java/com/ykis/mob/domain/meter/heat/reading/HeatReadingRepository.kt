package com.ykis.mob.domain.meter.heat.reading

import com.ykis.mob.data.remote.core.BaseResponse
import com.ykis.mob.data.remote.heat.reading.GetHeatReadingResponse
import com.ykis.mob.data.remote.heat.reading.GetLastHeatReadingResponse

interface HeatReadingRepository {
    suspend fun getHeatReadings(teplomerId:Int , uid:String) : GetHeatReadingResponse
    suspend fun getLastHeatReading(teplomerId: Int , uid:String) : GetLastHeatReadingResponse
    suspend fun addHeatReading(params: AddHeatReadingParams):BaseResponse
    suspend fun deleteLastHeatReading(readingId:Int ,uid:String):BaseResponse
}