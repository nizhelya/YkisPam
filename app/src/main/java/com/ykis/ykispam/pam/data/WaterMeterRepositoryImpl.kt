package com.ykis.ykispam.pam.data

import com.ykis.ykispam.pam.data.cache.user.UserCache
import com.ykis.ykispam.pam.data.cache.water.meter.WaterMeterCache
import com.ykis.ykispam.pam.data.remote.water.meter.WaterMeterRemote
import com.ykis.ykispam.pam.domain.family.request.BooleanInt
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import com.ykis.ykispam.pam.domain.type.flatMap
import com.ykis.ykispam.pam.domain.type.onNext
import com.ykis.ykispam.pam.domain.water.meter.WaterMeterEntity
import com.ykis.ykispam.pam.domain.water.meter.WaterMeterRepository
import javax.inject.Inject

class WaterMeterRepositoryImpl @Inject constructor(
    private val waterMeterCache: WaterMeterCache,
    private val waterMeterRemote: WaterMeterRemote,
    private val userCache: UserCache
) : WaterMeterRepository {
    override fun getWaterMeter(params: BooleanInt): Either<Failure, List<WaterMeterEntity>> {
        return userCache.getCurrentUser()
            .flatMap {
                return@flatMap if (params.needFetch) {
                    waterMeterRemote.getWaterMeter(params.int, it.userId, it.token)
                } else {
                    Either.Right(
                        waterMeterCache.getWaterMeter(params.int)
                    )
                }
            }
            .onNext {
                it.map {
                    waterMeterCache.insertWaterMeter(listOf(it))
                }
            }
    }
}