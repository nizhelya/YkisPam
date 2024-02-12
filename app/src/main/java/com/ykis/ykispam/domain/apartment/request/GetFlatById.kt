package com.ykis.ykispam.domain.apartment.request

import com.ykis.ykispam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.domain.apartment.ApartmentRepository
import com.ykis.ykispam.domain.interactor.UseCase
import javax.inject.Inject

class GetFlatById @Inject constructor(
    private val apartmentRepository: ApartmentRepository
) : UseCase<ApartmentEntity, Int>() {

    override suspend fun run(params: Int) = apartmentRepository.getFlatById(params)
}