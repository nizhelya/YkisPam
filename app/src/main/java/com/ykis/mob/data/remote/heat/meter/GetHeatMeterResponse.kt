package com.ykis.mob.data.remote.heat.meter

import com.squareup.moshi.Json
import com.ykis.mob.data.remote.core.BaseResponse
import com.ykis.mob.domain.meter.heat.meter.HeatMeterEntity

class GetHeatMeterResponse(
    success: Int,
    message: String,
    @Json(name = "heat_meters")
    val heatMeters: List<HeatMeterEntity>
) : BaseResponse(success, message)