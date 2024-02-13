package com.ykis.ykispam.data.remote.service

import com.ykis.ykispam.domain.service.ServiceEntity
import com.ykis.ykispam.domain.service.request.ServiceParams
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure

interface ServiceRemote {
    fun getFlatServices(
        uid: String,
        addressId: Int,
        houseId: Int,
        year: String,
        service: Byte,
        total: Byte,
    ): Either<Failure, List<ServiceEntity>>

    suspend fun newGetFlatServices(params : ServiceParams): List<ServiceEntity>
}

