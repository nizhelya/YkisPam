package com.ykis.ykispam.pam.data.remote.water.meter

import com.ykis.ykispam.pam.data.remote.api.ApiService
import com.ykis.ykispam.pam.data.remote.core.Request
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import com.ykis.ykispam.pam.domain.water.meter.WaterMeterEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WaterMeterRemoteImpl @Inject constructor(
    private val request: Request,
    private val apiService: ApiService
) : WaterMeterRemote {
    override fun getWaterMeter(
        addressId: Int,
        userId: Int,
        token: String
    ): Either<Failure, List<WaterMeterEntity>> {
        return request.make(
            apiService.getWaterMeter(
                createGetWaterMeterMap(
                    addressId,
                    userId,
                    token
                )
            )
        )
        {
            it.waterMeters
        }
    }


    private fun createGetWaterMeterMap(
        addressId: Int,
        userId: Int,
        token: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map.put(ApiService.ADDRESS_ID, addressId.toString())
        map.put(ApiService.PARAM_USER_ID, userId.toString())
        map.put(ApiService.PARAM_TOKEN, token)
        return map
    }


}