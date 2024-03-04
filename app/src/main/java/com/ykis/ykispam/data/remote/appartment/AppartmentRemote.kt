package com.ykis.ykispam.data.remote.appartment

import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.data.remote.core.BaseResponse
import com.ykis.ykispam.domain.apartment.ApartmentEntity

interface ApartmentRemote {
    suspend fun getApartmentList(uid:String) : GetApartmentsResponse
    suspend fun updateBti(params : ApartmentEntity) : BaseResponse
    suspend fun getApartment(addressId: Int , uid:String) : GetApartmentResponse
    suspend fun deleteApartment(addressId :Int , uid:String) : BaseResponse
    suspend fun addApartment(code:String , uid:String ,  email:String):GetSimpleResponse
}