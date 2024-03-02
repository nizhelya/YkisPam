package com.ykis.ykispam.data.remote.heat.meter

import com.squareup.moshi.Json
import com.ykis.ykispam.data.remote.core.BaseResponse
import com.ykis.ykispam.domain.meter.heat.meter.HeatMeterEntity

class GetHeatMeterResponse(
    success: Int,
    message: String,
    @Json(name = "heat_meters")
    val heatMeters: List<HeatMeterEntity>
) : BaseResponse(success, message)