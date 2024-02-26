package com.ykis.ykispam.data

import com.ykis.ykispam.data.cache.user.UserCache
import com.ykis.ykispam.data.cache.water.meter.WaterMeterCache
import com.ykis.ykispam.data.remote.water.meter.GetWaterMeterResponse
import com.ykis.ykispam.data.remote.water.meter.WaterMeterRemote
import com.ykis.ykispam.domain.family.request.BooleanInt
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import com.ykis.ykispam.domain.type.flatMap
import com.ykis.ykispam.domain.type.onNext
import com.ykis.ykispam.domain.water.meter.WaterMeterEntity
import com.ykis.ykispam.domain.water.meter.WaterMeterRepository
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
                    waterMeterRemote.getWaterMeter(params.int, it.uid)
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

    override suspend fun getWaterMeterList(addressId: Int, uid: String):GetWaterMeterResponse{
        return waterMeterRemote.getWaterMeterList(addressId,uid)
    }
}