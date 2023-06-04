package com.ykis.ykispam.pam.data.remote.service

import com.ykis.ykispam.pam.data.remote.core.BaseResponse
import com.ykis.ykispam.pam.domain.service.ServiceEntity

class GetServiceResponse(
    success: Int,
    message: String,
    val services: List<ServiceEntity>
) : BaseResponse(success, message)
