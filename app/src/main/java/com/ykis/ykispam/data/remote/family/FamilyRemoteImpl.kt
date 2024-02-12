package com.ykis.ykispam.data.remote.family

import com.ykis.ykispam.data.remote.core.Request
import com.ykis.ykispam.data.remote.api.ApiService
import com.ykis.ykispam.domain.family.FamilyEntity
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FamilyRemoteImpl @Inject constructor(
    private val request: Request,
    private val apiService: ApiService
) : FamilyRemote {


    private fun createGetFamilyFromFlatMap(
        addressId: Int,
        uid: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.ADDRESS_ID] = addressId.toString()
        map[ApiService.UID] = uid
        return map
    }

    override fun getFamilyFromFlat(
        addressId: Int,
        uid: String
    ): Either<Failure, List<FamilyEntity>> {
        return request.make(
            apiService.getFamilyFromFlat(
                createGetFamilyFromFlatMap(
                    addressId,
                    uid
                )
            )
        ) {
            it.family
        }
    }
}