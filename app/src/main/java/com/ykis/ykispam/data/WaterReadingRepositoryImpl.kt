package com.ykis.ykispam.data

import com.ykis.ykispam.data.cache.user.UserCache
import com.ykis.ykispam.data.cache.water.reading.WaterReadingCache
import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.data.remote.core.BaseResponse
import com.ykis.ykispam.data.remote.water.reading.GetLastWaterReadingResponse
import com.ykis.ykispam.data.remote.water.reading.GetWaterReadingsResponse
import com.ykis.ykispam.data.remote.water.reading.WaterReadingRemote
import com.ykis.ykispam.domain.meter.water.reading.AddWaterReadingParams
import com.ykis.ykispam.domain.meter.water.reading.WaterReadingRepository
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import com.ykis.ykispam.domain.type.flatMap
import javax.inject.Inject

class WaterReadingRepositoryImpl @Inject constructor(
    private val waterReadingCache: WaterReadingCache,
    private val waterReadingRemote: WaterReadingRemote,
    private val userCache: UserCache
) : WaterReadingRepository {

    override fun addNewWaterReading(params: AddWaterReadingParams): Either<Failure, GetSimpleResponse> {
        return userCache.getCurrentUser()
            .flatMap {
                return@flatMap waterReadingRemote.addNewWaterReading(
                    params.meterId,
                    params.newValue,
                    params.currentValue,
                    it.uid
                )
            }
    }


    override suspend fun getWaterReadings(vodomerId: Int, uid: String): GetWaterReadingsResponse {
        return waterReadingRemote.getWaterReadings(vodomerId, uid)
    }

    override suspend fun getLastWaterReading(
        vodomerId: Int,
        uid: String
    ): GetLastWaterReadingResponse {
        return waterReadingRemote.getLastWaterReading(vodomerId, uid)
    }

    override suspend fun addWaterReading(params: AddWaterReadingParams): BaseResponse {
        return waterReadingRemote.addWaterReading(params)
    }

    override suspend fun deleteLastWaterReading(readingId: Int , uid:String): BaseResponse {
        return waterReadingRemote.deleteLastReading(readingId , uid)
    }

}