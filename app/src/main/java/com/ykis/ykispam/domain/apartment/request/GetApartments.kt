package com.ykis.ykispam.domain.apartment.request

import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.data.cache.database.AppDatabase
import com.ykis.ykispam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.domain.apartment.ApartmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetApartmentList @Inject constructor(
    private val repository: ApartmentRepository,
    private val database : AppDatabase
){
    operator fun invoke (uid : String) : Flow<Resource<List<ApartmentEntity>?>>  = flow{
        val addressIdList = mutableListOf<Int>()
        try{
            emit(Resource.Loading())

            val response = repository.getApartmentList(uid)
            database.apartmentDao().deleteAllApartments()
            database.apartmentDao().insertApartmentList(response.apartments)
            for(i in response.apartments){
                addressIdList.add(i.addressId)
            }
            database.familyDao().deleteFamilyByApartment(addressIdList)
            database.serviceDao().deleteServiceByApartment(addressIdList)
            database.paymentDao().deletePaymentByApartment(addressIdList)
            database.waterMeterDao().deleteWaterMeterByApartment(addressIdList)
            database.heatMeterDao().deleteHeatMeterByApartment(addressIdList)
            database.heatReadingDao().deleteHeatReadingsByApartment(addressIdList)
            database.waterReadingDao().deleteWaterReadingByApartment(addressIdList)
            addressIdList.clear()
            emit(Resource.Success(response.apartments))

        }catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error!"))
        } catch (e: Exception) {
            val apartmentList = database.apartmentDao().getApartmentList()
            if (apartmentList.isNotEmpty()) {
                emit(Resource.Success(apartmentList))
                return@flow
            }
            emit(Resource.Error("Check your internet connection"))
        }
    }
}
