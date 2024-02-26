package com.ykis.ykispam.domain.apartment.request

import com.ykis.ykispam.R
import com.ykis.ykispam.core.ExceptionWithResourceMessage
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.domain.apartment.ApartmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddApartment @Inject constructor(
    private val repository: ApartmentRepository,
){
    operator fun invoke (code : String , uid:String ,email : String) : Flow<Resource<GetSimpleResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.addApartmentUser(code , uid , email)
            when {
                response.success == 1 -> emit(Resource.Success(response))
                response.message == "FlatAlreadyInDataBase" -> throw ExceptionWithResourceMessage(R.string.error_flat_in_db)
                response.message == "IncorrectCode" -> throw ExceptionWithResourceMessage(R.string.error_incorrect_code)
                else -> throw ExceptionWithResourceMessage(R.string.error_add_apartment)
            }
        }
        catch (e: ExceptionWithResourceMessage) {
            emit(Resource.Error(resourceMessage = e.resourceMessage,message = null))
        } catch (ex:Exception){
            emit(Resource.Error(message = ex.message ))
        }
    }
}