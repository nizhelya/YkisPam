package com.ykis.ykispam.data.remote.heat.meter

import com.ykis.ykispam.data.remote.api.ApiService
import com.ykis.ykispam.data.remote.core.Request
import com.ykis.ykispam.domain.heat.meter.HeatMeterEntity
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
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
        map[ApiService.ADDRESS_ID] = addressId.toString()
        map[ApiService.UID] = uid
        return map
    }
}
