package com.ykis.ykispam.data.remote.service

import com.ykis.ykispam.data.remote.api.ApiService
import com.ykis.ykispam.data.remote.core.Request
import com.ykis.ykispam.domain.service.ServiceEntity
import com.ykis.ykispam.domain.service.request.ServiceParams
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceRemoteImpl @Inject constructor(
    private val request: Request,
    private val apiService: ApiService
) : ServiceRemote {
    override fun getFlatServices(
        uid: String,
        addressId: Int,
        houseId: Int,
        year: String,
        service: Byte,
        total: Byte,
    ): Either<Failure, List<ServiceEntity>> {
        return request.make(
            apiService.getFlatService(
                createGetFlatServiceMap(
                    uid,
                    addressId,
                    houseId,
                    year,
                    service,
                    total,
                )
            )
        )
        {
            it.services
        }
    }

    override suspend fun newGetFlatServices(params: ServiceParams): List<ServiceEntity> {
        TODO("Not yet implemented")
    }



    private fun createGetFlatServiceMap(
        uid: String,
        addressId: Int,
        houseId: Int,
        year: String,
        service: Byte,
        total: Byte,
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.UID] = uid
        map[ApiService.ADDRESS_ID] = addressId.toString()
        map[ApiService.HOUSE_ID] = houseId.toString()
        map[ApiService.YEAR] = year
        map[ApiService.SERVICE] = service.toString()
        map[ApiService.TOTAL] = total.toString()
        return map
    }

}