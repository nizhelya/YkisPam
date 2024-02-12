package com.ykis.ykispam.data.remote.service

import com.ykis.ykispam.data.remote.core.BaseResponse
import com.ykis.ykispam.domain.service.ServiceEntity

class GetServiceResponse(
    success: Int,
    message: String,
    val services: List<ServiceEntity>
) : BaseResponse(success, message)
