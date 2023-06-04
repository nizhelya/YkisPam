package com.ykis.ykispam.pam.domain.appartment.request

import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.domain.appartment.AppartmentRepository
import com.ykis.ykispam.pam.domain.interactor.UseCase
import javax.inject.Inject

class DeleteFlatByUser @Inject constructor(
    private val appartmentRepository: AppartmentRepository
) : UseCase<GetSimpleResponse, Int>() {

    override suspend fun run(params: Int) = appartmentRepository.deleteFlatByUser(params)
}