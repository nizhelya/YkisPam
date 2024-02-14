package com.ykis.ykispam.data.remote.family

import com.ykis.ykispam.data.remote.core.Request
import com.ykis.ykispam.data.remote.api.ApiService
import com.ykis.ykispam.domain.family.FamilyEntity
import com.ykis.ykispam.domain.family.request.FamilyParams
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import retrofit2.await
import retrofit2.awaitResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FamilyRemoteImpl @Inject constructor(
    private val request: Request,
    private val apiService: ApiService
) : FamilyRemote {


    private fun createGetFamilyListMap(
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
            apiService.getFamilyList(
                createGetFamilyListMap(
                    addressId,
                    uid
                )
            )
        ) {
            it.family
        }
    }

    override suspend fun getFamilyList(params: FamilyParams): List<FamilyEntity> {
       return apiService.getFamilyList(
           createGetFamilyListMap(
               addressId = params.addressId,
               uid = params.uid
           )
       ).await().family
    }

}