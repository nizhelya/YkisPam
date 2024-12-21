package com.ykis.mob.data


import com.ykis.mob.data.remote.heat.meter.GetHeatMeterResponse
import com.ykis.mob.data.remote.heat.meter.HeatMeterRemote
import com.ykis.mob.domain.meter.heat.meter.HeatMeterRepository
import javax.inject.Inject

class HeatMeterRepositoryImpl @Inject constructor(
    private val heatMeterRemote: HeatMeterRemote,
) : HeatMeterRepository {
    override suspend fun getHeatMeterList(addressId: Int, uid: String): GetHeatMeterResponse {
        return heatMeterRemote.getHeatMeterList(addressId, uid)
    }
}
