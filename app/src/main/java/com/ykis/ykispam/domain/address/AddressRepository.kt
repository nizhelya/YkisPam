package com.ykis.ykispam.domain.address

import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure


interface AddressRepository {
    fun getBlocks(): Either<Failure, List<AddressEntity>>
    fun getStreetsFromBlock(blockId: Int): Either<Failure, List<AddressEntity>>
    fun getHousesFromStreet(streetId: Int, blockId: Int): Either<Failure, List<AddressEntity>>
    fun getFlatsFromHouse(houseId: Int): Either<Failure, List<AddressEntity>>
    fun addFlatByUser(kod:String): Either<Failure, GetSimpleResponse>
}