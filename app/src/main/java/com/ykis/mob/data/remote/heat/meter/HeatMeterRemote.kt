package com.ykis.mob.data.remote.heat.meter

interface HeatMeterRemote {
    suspend fun getHeatMeterList(addressId: Int,uid: String) : GetHeatMeterResponse
}