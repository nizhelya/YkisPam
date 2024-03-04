package com.ykis.ykispam.domain.apartment

import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.data.remote.appartment.GetApartmentResponse
import com.ykis.ykispam.data.remote.appartment.GetApartmentsResponse
import com.ykis.ykispam.data.remote.core.BaseResponse

interface ApartmentRepository {
    suspend fun getApartmentList(uid : String) : GetApartmentsResponse
    suspend fun updateBti(params : ApartmentEntity) : BaseResponse
    suspend fun getApartment(addressId: Int , uid:String) : GetApartmentResponse
    suspend fun deleteApartment(addressId: Int, uid:String) : BaseResponse
    suspend fun addApartmentUser(code:String , uid :String , email:String): GetSimpleResponse

}