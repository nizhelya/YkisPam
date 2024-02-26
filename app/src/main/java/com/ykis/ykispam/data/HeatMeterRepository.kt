package com.ykis.ykispam.data


import com.ykis.ykispam.data.cache.heat.meter.HeatMeterCache
import com.ykis.ykispam.data.cache.user.UserCache
import com.ykis.ykispam.data.remote.heat.meter.GetHeatMeterResponse
import com.ykis.ykispam.data.remote.heat.meter.HeatMeterRemote
import com.ykis.ykispam.domain.family.request.BooleanInt
import com.ykis.ykispam.domain.heat.meter.HeatMeterEntity
import com.ykis.ykispam.domain.heat.meter.HeatMeterRepository
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import com.ykis.ykispam.domain.type.flatMap
import com.ykis.ykispam.domain.type.onNext
import javax.inject.Inject

class HeatMeterRepositoryImpl @Inject constructor(
    private val heatMeterCache: HeatMeterCache,
    private val heatMeterRemote: HeatMeterRemote,
    private val userCache: UserCache
) : HeatMeterRepository {
    override fun getHeatMeter(params: BooleanInt): Either<Failure, List<HeatMeterEntity>> {
        return userCache.getCurrentUser()
            .flatMap {
                return@flatMap if (params.needFetch) {
                    heatMeterRemote.getHeatMeter(params.int, it.uid)
                } else {
                    Either.Right(
                        heatMeterCache.getHeatMeter(params.int)
                    )
                }
            }
            .onNext {
                it.map {
                    heatMeterCache.insertHeatMeter(listOf(it))
                }
            }
    }

    override suspend fun getHeatMeterList(addressId: Int, uid: String): GetHeatMeterResponse {
        return heatMeterRemote.getHeatMeterList(addressId, uid)
    }
}
