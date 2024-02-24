package com.ykis.ykispam.domain.apartment.request

import com.ykis.ykispam.R
import com.ykis.ykispam.core.ExceptionWithResourceMessage
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.data.remote.core.BaseResponse
import com.ykis.ykispam.domain.apartment.ApartmentRepository
import com.ykis.ykispam.domain.interactor.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class DeleteFlatByUser @Inject constructor(
    private val apartmentRepository: ApartmentRepository
) : UseCase<GetSimpleResponse, Int>() {

    override suspend fun run(params: Int) = apartmentRepository.deleteFlatByUser(params)
}

class DeleteApartment @Inject constructor(
    private val repository: ApartmentRepository,
){
    operator fun invoke (addressId:Int,uid:String) : Flow<Resource<BaseResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.deleteApartment(
                addressId,uid
            )
            if(response.success==1){
                emit(Resource.Success(response))
            }
            else throw ExceptionWithResourceMessage(R.string.error_delete_flat)
        } catch (e: ExceptionWithResourceMessage) {
            emit(Resource.Error(resourceMessage = e.resourceMessage,message = null))
        }catch (e:IOException){
            emit(Resource.Error(resourceMessage = R.string.error_network_delete))
        }
        catch (ex:Exception){
            emit(Resource.Error(message = ex.message ))
        }
    }
}