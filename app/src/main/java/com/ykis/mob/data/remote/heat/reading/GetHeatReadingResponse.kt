package com.ykis.mob.data.remote.heat.reading

import com.squareup.moshi.Json
import com.ykis.mob.data.remote.core.BaseResponse
import com.ykis.mob.domain.meter.heat.reading.HeatReadingEntity

class GetHeatReadingResponse(
    success: Int,
    message: String,
    @Json(name = "heat_readings")
    val heatReadings: List<HeatReadingEntity>
) : BaseResponse(success, message)