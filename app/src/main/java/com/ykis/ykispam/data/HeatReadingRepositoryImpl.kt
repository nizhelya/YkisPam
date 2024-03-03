package com.ykis.ykispam.data

import com.ykis.ykispam.data.cache.heat.reading.HeatReadingCache
import com.ykis.ykispam.data.cache.user.UserCache
import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.data.remote.core.BaseResponse
import com.ykis.ykispam.data.remote.heat.reading.GetHeatReadingResponse
import com.ykis.ykispam.data.remote.heat.reading.GetLastHeatReadingResponse
import com.ykis.ykispam.data.remote.heat.reading.HeatReadingRemote
import com.ykis.ykispam.domain.family.request.BooleanInt
import com.ykis.ykispam.domain.meter.heat.reading.HeatReadingEntity
import com.ykis.ykispam.domain.meter.heat.reading.HeatReadingRepository
import com.ykis.ykispam.domain.meter.heat.reading.request.AddHeatReadingParams
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import com.ykis.ykispam.domain.type.flatMap
import com.ykis.ykispam.domain.type.map
import com.ykis.ykispam.domain.type.onNext
import javax.inject.Inject

class HeatReadingRepositoryImpl @Inject constructor(
    private val heatReadingCache: HeatReadingCache,
    private val heatReadingRemote: HeatReadingRemote,
    private val userCache: UserCache
) : HeatReadingRepository {
    override fun getHeatReading(params: BooleanInt): Either<Failure, List<HeatReadingEntity>> {
        return userCache.getCurrentUser()
            .flatMap {
                return@flatMap if (params.needFetch) {
                    heatReadingRemote.getHeatReading(params.int, it.uid)
                } else {
                    Either.Right(
                        heatReadingCache.getHeatReading(params.int)
                    )
                }
            }
//            .onNext {
//                heatReadingCache.deleteAllReading()
//            }
            .map {
                it.sortedByDescending { it.pokId }
            }
            .onNext {
                it.map {
                    heatReadingCache.insertHeatReading(listOf(it))
                }
            }
    }

    override fun addNewHeatReading(params: AddHeatReadingParams): Either<Failure, GetSimpleResponse> {
        return userCache.getCurrentUser()
            .flatMap {
                return@flatMap heatReadingRemote.addNewHeatReading(
                    params.meterId,
                    params.newValue,
                    params.currentValue,
                    it.uid
                )
            }
    }

    override fun deleteCurrentHeatReading(params: Int): Either<Failure, GetSimpleResponse> {
        return userCache.getCurrentUser()
            .flatMap {
                return@flatMap heatReadingRemote.deleteCurrentHeatReading(
                    params, it.uid
                )
            }
    }

    override suspend fun getHeatReadings(teplomerId: Int, uid: String): GetHeatReadingResponse {
        return heatReadingRemote.getHeatReadings(teplomerId, uid)
    }

    override suspend fun getLastHeatReading(
        teplomerId: Int,
        uid: String
    ): GetLastHeatReadingResponse {
        return heatReadingRemote.getLastHeatReading(teplomerId, uid)
    }

    override suspend fun addHeatReading(params:  AddHeatReadingParams): BaseResponse {
        return heatReadingRemote.addHeatReading(params)
    }

}