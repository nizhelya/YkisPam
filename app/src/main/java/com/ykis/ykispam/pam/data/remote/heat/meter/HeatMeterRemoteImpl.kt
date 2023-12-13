package com.ykis.ykispam.pam.data.remote.heat.meter

import com.ykis.ykispam.pam.data.remote.api.ApiService
import com.ykis.ykispam.pam.data.remote.core.Request
import com.ykis.ykispam.pam.domain.heat.meter.HeatMeterEntity
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeatMeterRemoteImpl @Inject constructor(
    private val request: Request,
    private val apiService: ApiService
) : HeatMeterRemote {
    override fun getHeatMeter(
        addressId: Int,
        uid: String
    ): Either<Failure, List<HeatMeterEntity>> {
        return request.make(
            apiService.getHeatMeter(
                createGetHeatMeterMap(
                    addressId,
                    uid
                )
            )
        )
        {
            it.heatMeters
        }
    }


    private fun createGetHeatMeterMap(
        addressId: Int,
        uid: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map.put(ApiService.ADDRESS_ID, addressId.toString())
        map.put(ApiService.PARAM_USER_ID, uid)
        return map
    }
}
