package com.ykis.mob.domain.meter.heat.meter

import com.ykis.mob.data.remote.heat.meter.GetHeatMeterResponse

interface HeatMeterRepository {
    suspend fun getHeatMeterList(addressId : Int , uid:String) : GetHeatMeterResponse
}