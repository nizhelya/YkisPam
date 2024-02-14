package com.ykis.ykispam.domain.service.request

import android.util.Log
import com.google.android.play.integrity.internal.i
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.data.cache.database.AppDatabase
import com.ykis.ykispam.data.cache.service.ServiceCache
import com.ykis.ykispam.data.cache.user.UserCache
import com.ykis.ykispam.data.remote.service.ServiceRemote
import com.ykis.ykispam.domain.interactor.UseCase
import com.ykis.ykispam.domain.service.ServiceEntity
import com.ykis.ykispam.domain.service.ServiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import okhttp3.Dispatcher
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
                needFetch = params.needFetch
            ))
            database.serviceDao().insertService(response)
            delay(250)
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
