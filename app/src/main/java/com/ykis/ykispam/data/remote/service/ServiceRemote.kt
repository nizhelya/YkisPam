package com.ykis.ykispam.data.remote.service

import com.ykis.ykispam.domain.service.ServiceEntity
import com.ykis.ykispam.domain.service.request.ServiceParams
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure

interface ServiceRemote {
    suspend fun getFlatDetailServices(params : ServiceParams): List<ServiceEntity>
}

