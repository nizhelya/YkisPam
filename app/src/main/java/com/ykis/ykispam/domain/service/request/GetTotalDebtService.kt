package com.ykis.ykispam.domain.service.request

import com.ykis.ykispam.R
import com.ykis.ykispam.core.ExceptionWithResourceMessage
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.data.cache.database.AppDatabase
import com.ykis.ykispam.domain.service.ServiceEntity
import com.ykis.ykispam.domain.service.ServiceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/*class GetTotalDebtService @Inject constructor(
    private val serviceRepository: ServiceRepositoryImpl
) : UseCase<ServiceEntity?, Int>() {

    override suspend fun run(params: Int) = serviceRepository.getTotalFlatService(params)
}*/

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
        }
    }
}
