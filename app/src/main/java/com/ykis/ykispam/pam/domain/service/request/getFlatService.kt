package com.ykis.ykispam.pam.domain.service.request

import com.ykis.ykispam.pam.data.ServiceRepositoryImpl
import com.ykis.ykispam.pam.domain.interactor.UseCase
import com.ykis.ykispam.pam.domain.service.ServiceEntity
import javax.inject.Inject

class getFlatService @Inject constructor(
    private val serviceRepository: ServiceRepositoryImpl
) : UseCase<List<ServiceEntity>, ServiceParams>() {

    override suspend fun run(params: ServiceParams) = serviceRepository.getFlatService(params)
}
