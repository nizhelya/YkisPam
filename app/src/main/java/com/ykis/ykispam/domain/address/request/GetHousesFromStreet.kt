package com.ykis.ykispam.domain.address.request

import com.ykis.ykispam.domain.address.AddressEntity
import com.ykis.ykispam.domain.address.AddressRepository
import com.ykis.ykispam.domain.interactor.UseCase
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import javax.inject.Inject


class GetHousesFromStreet @Inject constructor(
    private val addressRepository: AddressRepository
) : UseCase<List<AddressEntity>, AddressEntity>() {
    override suspend fun run(params: AddressEntity): Either<Failure, List<AddressEntity>> =
        addressRepository.getHousesFromStreet(params.streetId, params.blockId)
}