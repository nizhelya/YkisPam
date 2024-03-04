package com.ykis.ykispam.data.remote.heat.reading

import com.ykis.ykispam.data.remote.core.BaseResponse
import com.ykis.ykispam.domain.meter.heat.reading.AddHeatReadingParams


interface HeatReadingRemote {
    suspend fun getHeatReadings(teplomerId: Int, uid:String):GetHeatReadingResponse
    suspend fun getLastHeatReading(teplomerId: Int , uid:String):GetLastHeatReadingResponse
    suspend fun addHeatReading(params : AddHeatReadingParams):BaseResponse
    suspend fun deleteLastHeatReading(readingId:Int , uid:String):BaseResponse

}