package com.ykis.ykispam.domain.service.request

import com.ykis.ykispam.data.ServiceRepositoryImpl
import com.ykis.ykispam.domain.interactor.UseCase
import com.ykis.ykispam.domain.service.ServiceEntity
import javax.inject.Inject

class GetTotalDebtService @Inject constructor(
    private val serviceRepository: ServiceRepositoryImpl
) : UseCase<ServiceEntity?, Int>() {

    override suspend fun run(params: Int) = serviceRepository.getTotalFlatService(params)
}