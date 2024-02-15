package com.ykis.ykispam.data.remote.appartment

import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.data.remote.core.Request
import com.ykis.ykispam.data.remote.api.ApiService
import com.ykis.ykispam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import retrofit2.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApartmentRemoteImpl @Inject constructor(
    private val request: Request,
    private val apiService: ApiService
) : ApartmentRemote {

    override fun getApartmentsByUser(uid: String): Either<Failure, List<ApartmentEntity>> {
        return request.make(
            apiService.getApartmentList(
                createGetApartmentListMap(uid)
            )
        ) {
            it.apartments
        }
    }


    override fun deleteFlatByUser(
        addressId: Int,
        uid: String
    ): Either<Failure, GetSimpleResponse> {
        return request.make(
            apiService.deleteFlatByUser(
                createRequestByAddressId(addressId,uid)
            )
        ) {
            it
        }
    }

    override fun updateBti(
        addressId: Int,
        phone: String,
        email: String,
        uid: String,
    ): Either<Failure, GetSimpleResponse> {
        return request.make(
            apiService.updateBti(
                createUpdateBti(
                    addressId,
                    phone,
                    email,
                    uid
                )
            )
        ) {
            it
        }
    }

    override fun getFlatById(
        addressId: Int,
        uid: String
    ): Either<Failure, ApartmentEntity> {
        return request.make(
            apiService.getFlatById(
                createRequestByAddressId(
                    addressId,uid)
            )
        ) {
            it.apartments[0]
        }
    }

    override suspend fun getApartmentList(uid: String): List<ApartmentEntity> {
        return apiService.getApartmentList(
            createGetApartmentListMap(
                uid
            )
        ).await().apartments
    }

    private fun createGetApartmentListMap(uid: String): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.UID] = uid
        return map
    }

    private fun createRequestByAddressId(
        addressId: Int,
        uid: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.PARAM_ADDRESS_ID] = addressId.toString()
        map[ApiService.UID] = uid
        return map
    }

    private fun createUpdateBti(
        addressId: Int,
        phone: String,
        email: String,
        uid: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.ADDRESS_ID] = addressId.toString()
        map[ApiService.PHONE] = phone
        map[ApiService.EMAIL] = email
        map[ApiService.UID] = uid
        return map
    }
}