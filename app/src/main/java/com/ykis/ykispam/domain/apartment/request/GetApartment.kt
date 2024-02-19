package com.ykis.ykispam.domain.apartment.request

import android.util.Log
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.core.snackbar.SnackbarMessage
import com.ykis.ykispam.data.cache.database.AppDatabase
import com.ykis.ykispam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.domain.apartment.ApartmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject


class GetApartment @Inject constructor(
    private val repository: ApartmentRepository,
    private val database: AppDatabase
){
    operator fun invoke (addressId : Int ,uid : String) : Flow<Resource<ApartmentEntity?>> = flow{
        try{
            emit(Resource.Loading())
            val response = repository.getApartment(addressId , uid)
            database.apartmentDao().insertApartmentList(listOf(response))
            emit(Resource.Success(response))
        }catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error!"))
        } catch (e: Exception) {
            val apartment = database.apartmentDao().getFlatById(addressId = addressId)
            emit(Resource.Success(apartment))
            SnackbarManager.showMessage(e.message.toString())
            emit(Resource.Error("Check your internet connection"))
        }
    }
}