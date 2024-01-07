package com.ykis.ykispam.pam.data.remote.service

import com.ykis.ykispam.pam.domain.service.ServiceEntity
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure

interface ServiceRemote {
    fun getFlatServices(
        uid: String,
        addressId: Int,
        houseId: Int,
        year: String,
        service: Byte,
        total: Byte,
    ): Either<Failure, List<ServiceEntity>>


}