package com.ykis.ykispam.data.remote.heat.reading

import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.data.remote.core.BaseResponse
import com.ykis.ykispam.domain.meter.heat.reading.HeatReadingEntity
import com.ykis.ykispam.domain.meter.heat.reading.request.AddHeatReadingParams
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure


interface HeatReadingRemote {
    fun getHeatReading(
        teplomerId: Int,
        uid: String
    ): Either<Failure, List<HeatReadingEntity>>

    fun addNewHeatReading(
        teplomerId: Int,
        newValue: Double,
        currentValue: Double,
        uid: String
    ): Either<Failure, GetSimpleResponse>

    fun deleteCurrentHeatReading(
        pokId: Int,
        uid: String
    ): Either<Failure, GetSimpleResponse>


    suspend fun getHeatReadings(teplomerId: Int, uid:String):GetHeatReadingResponse
    suspend fun getLastHeatReading(teplomerId: Int , uid:String):GetLastHeatReadingResponse
    suspend fun addHeatReading(params : AddHeatReadingParams):BaseResponse

}