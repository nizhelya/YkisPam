package com.ykis.ykispam.pam.domain.service.request

import com.ykis.ykispam.pam.data.ServiceRepositoryImpl
import com.ykis.ykispam.pam.domain.interactor.UseCase
import com.ykis.ykispam.pam.domain.service.ServiceEntity
import javax.inject.Inject

class getTotalDebtService @Inject constructor(
    private val serviceRepository: ServiceRepositoryImpl
) : UseCase<ServiceEntity?, Int>() {

    override suspend fun run(params: Int) = serviceRepository.getTotalFlatService(params)
}