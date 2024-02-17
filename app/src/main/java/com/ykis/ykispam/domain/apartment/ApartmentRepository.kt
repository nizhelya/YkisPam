package com.ykis.ykispam.domain.apartment

import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.data.remote.core.BaseResponse
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure

interface ApartmentRepository {
//    suspend  fun getApartmentsByUser(needFetch: Boolean): Either<Failure, List<ApartmentEntity>>
    fun deleteFlatByUser(addressId: Int): Either<Failure, GetSimpleResponse>
//    fun updateBti(addressId: Int, phone: String, email: String): Either<Failure, GetSimpleResponse>
    fun getFlatById(addressId: Int): Either<Failure, ApartmentEntity>

    suspend fun getApartmentList(uid : String) : List<ApartmentEntity>
    suspend fun updateBti(params : ApartmentEntity) : BaseResponse
    suspend fun getApartment(addressId: Int , uid:String) : ApartmentEntity
    suspend fun deleteApartment(addressId: Int, uid:String) : BaseResponse
    suspend fun addApartmentUser(code:String , uid :String): GetSimpleResponse

}