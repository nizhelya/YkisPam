package com.ykis.ykispam.data

import com.ykis.ykispam.data.remote.service.GetServiceResponse
import com.ykis.ykispam.data.remote.service.ServiceRemote
import com.ykis.ykispam.domain.service.ServiceRepository
import com.ykis.ykispam.domain.service.request.ServiceParams
import javax.inject.Inject

class ServiceRepositoryImpl @Inject constructor(
    private val serviceRemote : ServiceRemote
):ServiceRepository{
    override suspend fun getFlatDetailService(params: ServiceParams):GetServiceResponse{
        return serviceRemote.getFlatDetailServices(params)
    }
    override suspend fun getTotalDebtService(params: ServiceParams): GetServiceResponse {
        return serviceRemote.getTotalDebtService(params)
    }
}