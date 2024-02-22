package com.ykis.ykispam.domain.service.request

import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.data.cache.database.AppDatabase
import com.ykis.ykispam.domain.service.ServiceEntity
import com.ykis.ykispam.domain.service.ServiceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetFlatServices @Inject constructor(
    private val repository: ServiceRepository,
    private val database : AppDatabase
    ){
    operator fun invoke (params:ServiceParams) : Flow<Resource<List<ServiceEntity>?>> = flow{
        try{
            emit(Resource.Loading())
            val response = repository.getFlatDetailService(ServiceParams(
                uid = params.uid,
                addressId = params.addressId,
                houseId = params.houseId,
                year = params.year,
                service = params.service,
                total = params.total,
            ))
            database.serviceDao().insertService(response)
            emit(Resource.Success(response))
        }catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error!"))
        } catch (e: IOException) {
            val serviceDetailList = database.serviceDao().getServiceDetail(
                addressId = params.addressId,
                service =  when (params.service) {
                    1.toByte() -> "voda"
                    2.toByte() -> "teplo"
                    3.toByte() -> "tbo"
                    else -> "kv"
                },
                year = params.year
            )
            if (serviceDetailList.isNotEmpty()) {
                emit(Resource.Success(serviceDetailList))
                return@flow
            }
            emit(Resource.Error("Check your internet connection"))
        }
    }
}
