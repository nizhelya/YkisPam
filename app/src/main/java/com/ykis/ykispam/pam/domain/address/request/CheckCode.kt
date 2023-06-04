package com.ykis.ykispam.pam.domain.address.request

import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.domain.address.AddressRepository
import com.ykis.ykispam.pam.domain.interactor.UseCase
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import javax.inject.Inject

class CheckCode @Inject constructor(
    private val addressRepository: AddressRepository
) : UseCase<GetSimpleResponse, FlatCode>() {
    override suspend fun run(params: FlatCode): Either<Failure, GetSimpleResponse> =
        addressRepository.checkCode(params.kod, params.addressId)
}

data class FlatCode(
    val kod: String,
    val addressId: Int,
)