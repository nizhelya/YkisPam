package com.ykis.ykispam.domain.meter.heat.meter

import com.ykis.ykispam.data.remote.heat.meter.GetHeatMeterResponse

interface HeatMeterRepository {
    suspend fun getHeatMeterList(addressId : Int , uid:String) : GetHeatMeterResponse
}