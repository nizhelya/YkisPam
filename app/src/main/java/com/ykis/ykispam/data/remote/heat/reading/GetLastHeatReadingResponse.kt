package com.ykis.ykispam.data.remote.heat.reading

import com.squareup.moshi.Json
import com.ykis.ykispam.data.remote.core.BaseResponse
import com.ykis.ykispam.domain.meter.heat.reading.HeatReadingEntity

class GetLastHeatReadingResponse(
    success: Int,
    message: String,
    @Json(name = "heat_reading")
    val heatReading : HeatReadingEntity
) : BaseResponse(success, message)