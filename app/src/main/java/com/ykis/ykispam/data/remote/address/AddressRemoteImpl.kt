package com.ykis.ykispam.data.remote.address

import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.data.remote.core.Request
import com.ykis.ykispam.data.remote.api.ApiService
import com.ykis.ykispam.domain.address.AddressEntity
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressRemoteImpl @Inject constructor(
    private val request: Request,
    private val apiService: ApiService
) : AddressRemote {

    override fun getBlocks(
        uid: String
    ): Either<Failure, List<AddressEntity>> {
        return request.make(
            apiService.getBlocks(
                createGetBlocksMap(
                    uid
                )
            )
        ) {
            it.address
        }
    }

    override fun getStreetsFromBlock(
        blockId: Int,
        uid: String
    ): Either<Failure, List<AddressEntity>> {
        return request.make(
            apiService.getStreetsFromBlock(
                createGetStreetsMap(
                    blockId,
                    uid
                )
            )
        ) {
            it.address
        }
    }

    override fun getHousesFromStreet(
        streetId: Int,
        blockId: Int,
        uid: String
    ): Either<Failure, List<AddressEntity>> {
        return request.make(
            apiService.getHousesFromStreet(
                createGetHousesMap(
                    streetId,
                    blockId,
                    uid
                )
            )
        ) {
            it.address
        }
    }

    override fun getFlatsFromHouse(
        houseId: Int,
        uid: String
    ): Either<Failure, List<AddressEntity>> {
        return request.make(
            apiService.getFlatsFromHouse(
                createGetFlatsMap(
                    houseId,
                    uid
                )
            )
        ) {
            it.address
        }
    }

    override fun addFlatsByUser(
        kod: String,
        uid: String,
        email:String
    ): Either<Failure, GetSimpleResponse> {
        return request.make(
            apiService.addFlatsByUser(
                createAddFlatsMap(
                    kod,
                    uid,
                    email
                )
            )
        )
        {
            it
        }

    }




    private fun createGetBlocksMap(uid: String): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.UID] = uid
        return map
    }

    private fun createGetStreetsMap(blockId: Int, uid: String): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.BLOCK_ID] = blockId.toString()
        map[ApiService.UID] = uid
        return map
    }

    private fun createGetHousesMap(
        streetId: Int,
        blockId: Int,
        uid: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.STREET_ID] = streetId.toString()
        map[ApiService.BLOCK_ID] = blockId.toString()
        map[ApiService.UID] = uid

        return map
    }

    private fun createGetFlatsMap(houseId: Int,uid: String): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.HOUSE_ID] = houseId.toString()
        map[ApiService.UID] = uid

        return map
    }

    private fun createAddFlatsMap(kod: String, uid: String,email: String): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.KOD] = kod
        map[ApiService.UID] = uid
        map[ApiService.EMAIL] = email


        return map
    }

}