package com.ykis.ykispam.data

import com.ykis.ykispam.data.remote.water.meter.GetWaterMeterResponse
import com.ykis.ykispam.data.remote.water.meter.WaterMeterRemote
import com.ykis.ykispam.domain.water.meter.WaterMeterRepository
import javax.inject.Inject

class WaterMeterRepositoryImpl @Inject constructor(
    private val waterMeterRemote: WaterMeterRemote,
) : WaterMeterRepository {
    override suspend fun getWaterMeterList(addressId: Int, uid: String):GetWaterMeterResponse{
        return waterMeterRemote.getWaterMeterList(addressId,uid)
    }
}