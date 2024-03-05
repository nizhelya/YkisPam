package com.ykis.ykispam.data.remote.service

import com.ykis.ykispam.domain.service.request.ServiceParams

interface ServiceRemote {
    suspend fun getFlatDetailServices(params : ServiceParams):GetServiceResponse
    suspend fun getTotalDebtService(params: ServiceParams):GetServiceResponse
}

