package com.ykis.ykispam.pam.data.remote.heat.reading

import com.squareup.moshi.Json
import com.ykis.ykispam.pam.data.remote.core.BaseResponse
import com.ykis.ykispam.pam.domain.heat.reading.HeatReadingEntity

class GetHeatReadingResponse(
    success: Int,
    message: String,
    @Json(name = "heat_readings")
    val heatReadings: List<HeatReadingEntity>
) : BaseResponse(success, message)