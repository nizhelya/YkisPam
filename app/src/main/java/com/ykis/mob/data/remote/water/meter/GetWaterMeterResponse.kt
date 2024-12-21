package com.ykis.mob.data.remote.water.meter

import com.squareup.moshi.Json
import com.ykis.mob.data.remote.core.BaseResponse
import com.ykis.mob.domain.meter.water.meter.WaterMeterEntity

class GetWaterMeterResponse(
    success: Int,
    message: String,
    @Json(name = "water_meters")
    val waterMeters: List<WaterMeterEntity>
) : BaseResponse(success, message)