package com.ykis.mob.domain.service

import com.ykis.mob.data.remote.service.GetServiceResponse
import com.ykis.mob.domain.service.request.ServiceParams

interface ServiceRepository {
    suspend fun getFlatDetailService(params: ServiceParams): GetServiceResponse
    suspend fun getTotalDebtService(params: ServiceParams):GetServiceResponse
}