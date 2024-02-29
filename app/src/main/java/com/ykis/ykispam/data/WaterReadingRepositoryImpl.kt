package com.ykis.ykispam.data

import com.ykis.ykispam.data.cache.user.UserCache
import com.ykis.ykispam.data.cache.water.reading.WaterReadingCache
import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.data.remote.water.reading.GetWaterReadingResponse
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
    override fun getWaterReading(params: BooleanInt): Either<Failure, List<WaterReadingEntity>> {
        return userCache.getCurrentUser()
            .flatMap {
                return@flatMap if (params.needFetch) {
                    waterReadingRemote.getWaterReading(params.int, it.uid)
                } else {
                    Either.Right(
                        waterReadingCache.getWaterReading(params.int)
                    )
                }
            }
//            .onNext {
//                waterReadingCache.deleteAllReading()
//            }
            .map {
                it.sortedByDescending { it.pokId }
            }
            .onNext {
                it.map {
                    waterReadingCache.insertWaterReading(listOf(it))
                }
            }
    }

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

    override suspend fun getWaterReadings(vodomerId: Int, uid: String): GetWaterReadingResponse {
        return waterReadingRemote.getWaterReadings(vodomerId, uid)
    }

}