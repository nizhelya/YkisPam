package com.ykis.ykispam.data

import com.ykis.ykispam.data.remote.service.ServiceRemote
import com.ykis.ykispam.domain.service.ServiceEntity
import com.ykis.ykispam.domain.service.ServiceRepository
import com.ykis.ykispam.domain.service.request.ServiceParams
import javax.inject.Inject

class ServiceRepositoryImpl @Inject constructor(
    private val api : ServiceRemote
):ServiceRepository{
    override suspend fun getFlatDetailService(params: ServiceParams): List<ServiceEntity> {
        return api.getFlatDetailServices(params)
    }
}