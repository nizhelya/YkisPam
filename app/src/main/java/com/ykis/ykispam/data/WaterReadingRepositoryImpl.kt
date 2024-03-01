package com.ykis.ykispam.data

import com.ykis.ykispam.data.cache.user.UserCache
import com.ykis.ykispam.data.cache.water.reading.WaterReadingCache
import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.data.remote.water.reading.GetLastWaterReadingResponse
import com.ykis.ykispam.data.remote.water.reading.GetWaterReadingsResponse
import com.ykis.ykispam.data.remote.water.reading.WaterReadingRemote
import com.ykis.ykispam.domain.family.request.BooleanInt
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import com.ykis.ykispam.domain.type.flatMap
import com.ykis.ykispam.domain.type.map
import com.ykis.ykispam.domain.type.onNext
import com.ykis.ykispam.domain.water.reading.WaterReadingEntity
import com.ykis.ykispam.domain.water.reading.WaterReadingRepository
import com.ykis.ykispam.domain.water.reading.request.AddReadingParams
import javax.inject.Inject

class WaterReadingRepositoryImpl @Inject constructor(
    private val waterReadingCache: WaterReadingCache,
    private val waterReadingRemote: WaterReadingRemote,
    private val userCache: UserCache
) : WaterReadingRepository {

    override fun addNewWaterReading(params: AddReadingParams): Either<Failure, GetSimpleResponse> {
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

    override fun deleteCurrentWaterReading(params: Int): Either<Failure, GetSimpleResponse> {
        return userCache.getCurrentUser()
            .flatMap {
                return@flatMap waterReadingRemote.deleteCurrentWaterReading(
                    params,  it.uid
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

}