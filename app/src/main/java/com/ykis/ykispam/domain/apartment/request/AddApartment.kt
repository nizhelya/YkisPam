package com.ykis.ykispam.domain.apartment.request

import com.ykis.ykispam.R
import com.ykis.ykispam.core.ExceptionWithResourceMessage
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.data.remote.core.BaseResponse
import com.ykis.ykispam.domain.address.AddressRepository
import com.ykis.ykispam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.domain.apartment.ApartmentRepository
import com.ykis.ykispam.domain.interactor.UseCase
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddApartment @Inject constructor(
    private val repository: ApartmentRepository,
){
    operator fun invoke (code : String , uid:String) : Flow<Resource<GetSimpleResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.addApartmentUser(code , uid)
            if(response.success==1){
                emit(Resource.Success(response))
            }else if(response.message == "FlatAlreadyInDataBase") {
                throw ExceptionWithResourceMessage(R.string.error_flat_in_db)
            }else throw ExceptionWithResourceMessage(R.string.error_add_apartment)
        }
        catch (e: ExceptionWithResourceMessage) {
            emit(Resource.Error(resourceMessage = e.resourceMessage,message = null))
        } catch (ex:Exception){
            emit(Resource.Error(message = ex.message ))
        }
    }
}