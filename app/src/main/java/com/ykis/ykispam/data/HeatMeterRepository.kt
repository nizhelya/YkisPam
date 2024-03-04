package com.ykis.ykispam.data


import com.ykis.ykispam.data.remote.heat.meter.GetHeatMeterResponse
import com.ykis.ykispam.data.remote.heat.meter.HeatMeterRemote
import com.ykis.ykispam.domain.meter.heat.meter.HeatMeterRepository
import javax.inject.Inject

class HeatMeterRepositoryImpl @Inject constructor(
    private val heatMeterRemote: HeatMeterRemote,
) : HeatMeterRepository {
    override suspend fun getHeatMeterList(addressId: Int, uid: String): GetHeatMeterResponse {
        return heatMeterRemote.getHeatMeterList(addressId, uid)
    }
}
