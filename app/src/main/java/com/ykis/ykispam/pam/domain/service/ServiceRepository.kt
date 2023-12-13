package com.ykis.ykispam.pam.domain.service

import com.ykis.ykispam.pam.domain.service.request.ServiceParams
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure

interface ServiceRepository {
    fun getFlatService(params: ServiceParams): Either<Failure, List<ServiceEntity>>
    fun getTotalFlatService(addressId: Int): Either<Failure, ServiceEntity?>
}