package com.ykis.mob.data.remote.service

import com.ykis.mob.domain.service.request.ServiceParams

interface ServiceRemote {
    suspend fun getFlatDetailServices(params : ServiceParams):GetServiceResponse
    suspend fun getTotalDebtService(params: ServiceParams):GetServiceResponse
}

