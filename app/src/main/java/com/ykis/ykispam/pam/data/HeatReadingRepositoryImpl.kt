package com.ykis.ykispam.pam.data

import com.ykis.ykispam.pam.data.cache.heat.reading.HeatReadingCache
import com.ykis.ykispam.pam.data.cache.user.UserCache
import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.data.remote.heat.reading.HeatReadingRemote
import com.ykis.ykispam.pam.domain.family.request.BooleanInt
import com.ykis.ykispam.pam.domain.heat.reading.HeatReadingEntity
import com.ykis.ykispam.pam.domain.heat.reading.HeatReadingRepository
import com.ykis.ykispam.pam.domain.heat.reading.request.AddHeatReadingParams
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import com.ykis.ykispam.pam.domain.type.flatMap
import com.ykis.ykispam.pam.domain.type.map
import com.ykis.ykispam.pam.domain.type.onNext
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
                    heatReadingRemote.getHeatReadings(params.int, it.uid)
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

}