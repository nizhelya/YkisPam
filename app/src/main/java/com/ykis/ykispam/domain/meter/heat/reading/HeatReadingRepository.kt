package com.ykis.ykispam.domain.meter.heat.reading

import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.data.remote.core.BaseResponse
import com.ykis.ykispam.data.remote.heat.reading.GetHeatReadingResponse
import com.ykis.ykispam.data.remote.heat.reading.GetLastHeatReadingResponse
import com.ykis.ykispam.domain.family.request.BooleanInt
import com.ykis.ykispam.domain.meter.heat.reading.request.AddHeatReadingParams
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure

interface HeatReadingRepository {
    fun getHeatReading(params: BooleanInt): Either<Failure, List<HeatReadingEntity>>
    fun addNewHeatReading(params: AddHeatReadingParams): Either<Failure, GetSimpleResponse>
    fun deleteCurrentHeatReading(params: Int): Either<Failure, GetSimpleResponse>

    suspend fun getHeatReadings(teplomerId:Int , uid:String) : GetHeatReadingResponse
    suspend fun getLastHeatReading(teplomerId: Int , uid:String) : GetLastHeatReadingResponse
    suspend fun addHeatReading(params: AddHeatReadingParams):BaseResponse
}