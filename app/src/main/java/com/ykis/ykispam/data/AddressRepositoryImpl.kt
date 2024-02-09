package com.ykis.ykispam.pam.data

import com.ykis.ykispam.pam.data.cache.user.UserCache
import com.ykis.ykispam.pam.domain.address.AddressEntity
import com.ykis.ykispam.pam.domain.address.AddressRepository
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import com.ykis.ykispam.pam.domain.type.flatMap
import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.data.remote.address.AddressRemote
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(
    private val remote: AddressRemote,
    private val userCache: UserCache
) : AddressRepository {
    override fun getBlocks(): Either<Failure, List<AddressEntity>> {
        return userCache.getCurrentUser()
            .flatMap { return@flatMap remote.getBlocks(it.uid) }
    }

    override fun getStreetsFromBlock(blockId: Int): Either<Failure, List<AddressEntity>> {
        return userCache.getCurrentUser()
            .flatMap { return@flatMap remote.getStreetsFromBlock(blockId, it.uid) }
    }

    override fun getHousesFromStreet(
        streetId: Int,
        blockId: Int
    ): Either<Failure, List<AddressEntity>> {
        return userCache.getCurrentUser()
            .flatMap {
                return@flatMap remote.getHousesFromStreet(
                    streetId,
                    blockId,
                    it.uid
                )
            }
    }

    override fun getFlatsFromHouse(houseId: Int): Either<Failure, List<AddressEntity>> {
        return userCache.getCurrentUser()
            .flatMap { return@flatMap remote.getFlatsFromHouse(houseId,it.uid) }
    }


    override fun addFlatByUser(kod: String): Either<Failure, GetSimpleResponse> {
        return userCache.getCurrentUser()
            .flatMap { return@flatMap remote.addFlatsByUser(kod,it.uid,it.email) }
    }

}