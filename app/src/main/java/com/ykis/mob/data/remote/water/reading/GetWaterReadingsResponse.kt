package com.ykis.mob.data.remote.water.reading

import com.squareup.moshi.Json
import com.ykis.mob.data.remote.core.BaseResponse
import com.ykis.mob.domain.meter.water.reading.WaterReadingEntity

class GetWaterReadingsResponse(
    success: Int,
    message: String,
    @Json(name = "water_readings")
    val waterReadings: List<WaterReadingEntity>
) : BaseResponse(success, message)