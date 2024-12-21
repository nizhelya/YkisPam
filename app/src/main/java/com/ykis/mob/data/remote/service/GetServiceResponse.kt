package com.ykis.mob.data.remote.service

import com.ykis.mob.data.remote.core.BaseResponse
import com.ykis.mob.domain.service.ServiceEntity

class GetServiceResponse(
    success: Int,
    message: String,
    val services: List<ServiceEntity>
) : BaseResponse(success, message)
