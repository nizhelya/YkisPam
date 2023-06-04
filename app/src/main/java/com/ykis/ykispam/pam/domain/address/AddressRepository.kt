package com.ykis.ykispam.pam.domain.address

import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure


interface AddressRepository {
    fun getBlocks(): Either<Failure, List<AddressEntity>>
    fun getStreetsFromBlock(blockId: Int): Either<Failure, List<AddressEntity>>
    fun getHousesFromStreet(streetId: Int, blockId: Int): Either<Failure, List<AddressEntity>>
    fun getFlatsFromHouse(houseId: Int): Either<Failure, List<AddressEntity>>
    fun addFlatByUser(addressId: Int): Either<Failure, GetSimpleResponse>
    fun checkCode(kod: String, addressId: Int): Either<Failure, GetSimpleResponse>
}