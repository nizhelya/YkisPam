package com.ykis.ykispam.data

import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.data.remote.appartment.ApartmentRemote
import com.ykis.ykispam.data.remote.appartment.GetApartmentResponse
import com.ykis.ykispam.data.remote.appartment.GetApartmentsResponse
import com.ykis.ykispam.data.remote.core.BaseResponse
import com.ykis.ykispam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.domain.apartment.ApartmentRepository
import javax.inject.Inject


class ApartmentRepositoryImpl @Inject constructor(
    private val apartmentRemote: ApartmentRemote
) : ApartmentRepository {

    override suspend fun getApartmentList(uid: String): GetApartmentsResponse {
        return apartmentRemote.getApartmentList(uid)
    }

    override suspend fun updateBti(params : ApartmentEntity): BaseResponse {
        return apartmentRemote.updateBti(params)
    }

    override suspend fun getApartment(addressId: Int, uid: String): GetApartmentResponse {
        return apartmentRemote.getApartment(addressId , uid)
    }

    override suspend fun deleteApartment(addressId: Int, uid: String): BaseResponse {
        return apartmentRemote.deleteApartment(addressId , uid)
    }

    override suspend fun addApartmentUser(code: String ,uid :String , email : String): GetSimpleResponse {
        return apartmentRemote.addApartment(code , uid , email)
    }
}