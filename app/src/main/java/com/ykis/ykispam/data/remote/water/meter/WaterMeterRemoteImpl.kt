package com.ykis.ykispam.data.remote.water.meter

import com.ykis.ykispam.data.remote.api.ApiService
import com.ykis.ykispam.data.remote.core.Request
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import com.ykis.ykispam.domain.water.meter.WaterMeterEntity
import retrofit2.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WaterMeterRemoteImpl @Inject constructor(
    private val request: Request,
    private val apiService: ApiService
) : WaterMeterRemote {
    override fun getWaterMeter(
        addressId: Int,
        uid: String
    ): Either<Failure, List<WaterMeterEntity>> {
        return request.make(
            apiService.getWaterMeterList(
                createGetWaterMeterMap(
                    addressId,
                    uid
                )
            )
        )
        {
            it.waterMeters
        }
    }

    override suspend fun getWaterMeterList(addressId: Int, uid: String): GetWaterMeterResponse {
        return apiService.getWaterMeterList(
            createGetWaterMeterMap(
                addressId = addressId,
                uid = uid
            )
        ).await()
    }


    private fun createGetWaterMeterMap(
        addressId: Int,
        uid: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.ADDRESS_ID] = addressId.toString()
        map[ApiService.UID] = uid
        return map
    }


}