package com.ykis.ykispam.domain.apartment.request

import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.domain.apartment.ApartmentRepository
import com.ykis.ykispam.domain.interactor.UseCase
import javax.inject.Inject

class DeleteFlatByUser @Inject constructor(
    private val apartmentRepository: ApartmentRepository
) : UseCase<GetSimpleResponse, Int>() {

    override suspend fun run(params: Int) = apartmentRepository.deleteFlatByUser(params)
}
