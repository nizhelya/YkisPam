package com.ykis.ykispam.domain.service

import com.ykis.ykispam.domain.service.request.ServiceParams
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure

interface ServiceRepository {
    fun getFlatService(params: ServiceParams): Either<Failure, List<ServiceEntity>>

    // TODO: remove get totalFlat 
    fun getTotalFlatService(addressId: Int): Either<Failure, ServiceEntity?>

    suspend fun newGetFlatService(params: ServiceParams): List<ServiceEntity>
}