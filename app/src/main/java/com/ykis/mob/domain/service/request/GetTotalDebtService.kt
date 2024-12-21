package com.ykis.mob.domain.service.request

import com.ykis.mob.R
import com.ykis.mob.core.ExceptionWithResourceMessage
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

class GetTotalDebtServices @Inject constructor(
    private val repository: ServiceRepository,
    private val database : AppDatabase
){
    operator fun invoke (params:ServiceParams) : Flow<Resource<ServiceEntity>> = flow{
        try{
            emit(Resource.Loading())
            val response = repository.getTotalDebtService(ServiceParams(
                uid = params.uid,
                addressId = params.addressId,
                houseId = params.houseId,
                year = params.year,
                service = params.service,
                total = params.total,
            ))
            if(response.success == 1 && response.services.isNotEmpty()){
                database.serviceDao().insertService(response.services)
                emit(Resource.Success(response.services[0]))
            }else throw ExceptionWithResourceMessage(R.string.generic_error)
        }catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error!"))
        } catch (e: IOException) {
            emit(Resource.Error("Check your internet connection"))
            val totalDebt = database.serviceDao().getTotalDebt(params.addressId)
            if(totalDebt!=null){
                emit(Resource.Success(totalDebt))
            }
        }catch (e:ExceptionWithResourceMessage
        ){
            SnackbarManager.showMessage(e.resourceMessage)
        }
    }
}
