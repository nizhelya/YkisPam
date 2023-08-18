package com.ykis.ykispam.pam.data

import com.ykis.ykispam.pam.data.cache.heat.meter.HeatMeterCache
import com.ykis.ykispam.pam.data.cache.user.UserCache
import com.ykis.ykispam.pam.domain.family.request.BooleanInt
import com.ykis.ykispam.pam.domain.heat.meter.HeatMeterEntity
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.flatMap
import com.ykis.ykispam.pam.domain.type.onNext
import com.ykis.ykispam.pam.data.remote.heat.meter.HeatMeterRemote
import com.ykis.ykispam.pam.domain.heat.meter.HeatMeterRepository
import com.ykis.ykispam.pam.domain.type.Failure


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
}
