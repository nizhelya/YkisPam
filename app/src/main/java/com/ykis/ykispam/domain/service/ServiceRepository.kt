package com.ykis.ykispam.domain.service

import com.ykis.ykispam.domain.service.request.ServiceParams
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure

interface ServiceRepository {
    suspend fun getFlatDetailService(params: ServiceParams): List<ServiceEntity>
}