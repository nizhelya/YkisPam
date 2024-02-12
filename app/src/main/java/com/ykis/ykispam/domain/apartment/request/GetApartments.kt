package com.ykis.ykispam.domain.apartment.request

import com.ykis.ykispam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.domain.apartment.ApartmentRepository
import com.ykis.ykispam.domain.interactor.UseCase
import javax.inject.Inject

class GetApartments @Inject constructor(
    private val apartmentRepository: ApartmentRepository
) : UseCase<List<ApartmentEntity>, Boolean>() {

    override suspend fun run(needFetch: Boolean) =
        apartmentRepository.getApartmentsByUser(needFetch)
}