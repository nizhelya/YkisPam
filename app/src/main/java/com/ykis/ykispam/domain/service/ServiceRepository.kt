package com.ykis.ykispam.domain.service

import com.ykis.ykispam.data.remote.service.GetServiceResponse
import com.ykis.ykispam.domain.service.request.ServiceParams

interface ServiceRepository {
    suspend fun getFlatDetailService(params: ServiceParams): List<ServiceEntity>
    suspend fun getTotalDebtService(params: ServiceParams):GetServiceResponse
}