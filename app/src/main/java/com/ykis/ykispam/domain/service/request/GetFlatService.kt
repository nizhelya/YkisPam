package com.ykis.ykispam.domain.service.request

import com.ykis.ykispam.data.ServiceRepositoryImpl
import com.ykis.ykispam.domain.interactor.UseCase
import com.ykis.ykispam.domain.service.ServiceEntity
import javax.inject.Inject

class GetFlatService @Inject constructor(
    private val serviceRepository: ServiceRepositoryImpl
) : UseCase<List<ServiceEntity>, ServiceParams>() {

    override suspend fun run(params: ServiceParams) = serviceRepository.getFlatService(params)
}
