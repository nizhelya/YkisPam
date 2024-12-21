package com.ykis.mob.domain.service.request

import com.ykis.mob.R
import com.ykis.mob.core.Resource
import com.ykis.mob.core.snackbar.SnackbarManager
import com.ykis.mob.data.cache.database.AppDatabase
import com.ykis.mob.domain.service.ServiceEntity
import com.ykis.mob.domain.service.ServiceRepository
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
            database.serviceDao().getServiceDetail(
                params.addressId,
                when(params.service){
                    1.toByte() -> "voda"
                    2.toByte() -> "teplo"
                    3.toByte() -> "tbo"
                    else -> "kv"
                },
                params.year
            )
            if(response.success==1){
                database.serviceDao().insertService(response.services)
                emit(Resource.Success(response.services))
            }
        }catch (e: HttpException) {
            SnackbarManager.showMessage(e.message())
            emit(Resource.Error())
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
            SnackbarManager.showMessage(R.string.error_network)
            emit(Resource.Error())
        }
    }
}
