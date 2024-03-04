package com.ykis.ykispam.data.remote.family

import com.ykis.ykispam.data.remote.api.ApiService
import com.ykis.ykispam.domain.family.FamilyEntity
import com.ykis.ykispam.domain.family.request.FamilyParams
import retrofit2.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FamilyRemoteImpl @Inject constructor(
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


    override suspend fun getFamilyList(params: FamilyParams): List<FamilyEntity> {
       return apiService.getFamilyList(
           createGetFamilyListMap(
               addressId = params.addressId,
               uid = params.uid
           )
       ).await().family
    }

}