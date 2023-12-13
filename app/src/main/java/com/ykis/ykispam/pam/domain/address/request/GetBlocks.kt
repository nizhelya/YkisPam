package com.ykis.ykispam.pam.domain.address.request

import com.ykis.ykispam.pam.domain.address.AddressEntity
import com.ykis.ykispam.pam.domain.address.AddressRepository
import com.ykis.ykispam.pam.domain.interactor.UseCase
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import com.ykis.ykispam.pam.domain.type.None
import javax.inject.Inject

class GetBlocks @Inject constructor(
    private val addressRepository: AddressRepository
) : UseCase<List<AddressEntity>, None>() {
    override suspend fun run(params: None): Either<Failure, List<AddressEntity>> =
        addressRepository.getBlocks()
}