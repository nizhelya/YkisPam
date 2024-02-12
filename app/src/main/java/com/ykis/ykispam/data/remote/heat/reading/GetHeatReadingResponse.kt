package com.ykis.ykispam.data.remote.heat.reading

import com.squareup.moshi.Json
import com.ykis.ykispam.data.remote.core.BaseResponse
import com.ykis.ykispam.domain.heat.reading.HeatReadingEntity

class GetHeatReadingResponse(
    success: Int,
    message: String,
    @Json(name = "heat_readings")
    val heatReadings: List<HeatReadingEntity>
) : BaseResponse(success, message)