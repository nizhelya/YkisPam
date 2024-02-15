package com.ykis.ykispam.domain.apartment.request

import android.util.Log
import com.ykis.ykispam.R
import com.ykis.ykispam.core.ExceptionWithResourceMessage
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.data.cache.database.AppDatabase
import com.ykis.ykispam.data.remote.core.BaseResponse
import com.ykis.ykispam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.domain.apartment.ApartmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.ConnectException
import javax.inject.Inject

class UpdateBti @Inject constructor(
    private val repository: ApartmentRepository,
){
    operator fun invoke (params: ApartmentEntity) : Flow<Resource<BaseResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.newUpdateBti(
                params
            )
            if(response.success==1){
                emit(Resource.Success(response))
            }else throw ExceptionWithResourceMessage(R.string.error_update)
        } catch (e: ExceptionWithResourceMessage) {
            emit(Resource.Error(resourceMessage = e.resourceMessage,message = null))
        }catch (ex:Exception){
            emit(Resource.Error(message = ex.message ))
        }
    }
}
