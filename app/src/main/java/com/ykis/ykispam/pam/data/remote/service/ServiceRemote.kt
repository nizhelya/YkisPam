package com.ykis.ykispam.pam.data.remote.service

import com.ykis.ykispam.pam.domain.service.ServiceEntity
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure

interface ServiceRemote {
    fun getFlatServices(
        addressId: Int,
        houseId: Int,
        qty: Byte,
        service: Byte,
        total: Byte,
        userId: Int,
        token: String
    ): Either<Failure, List<ServiceEntity>>
}