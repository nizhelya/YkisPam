package com.ykis.ykispam.domain

import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.data.cache.database.AppDatabase
import com.ykis.ykispam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.domain.apartment.ApartmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ClearDatabase @Inject constructor(
    private val database : AppDatabase
){
    operator fun invoke () : Flow<Resource<String>> = flow{
        try{
            emit(Resource.Loading())
            database.familyDao().deleteAllFamily()
            database.serviceDao().deleteAllService()
            database.paymentDao().deleteAllPayment()
            database.waterMeterDao().deleteAllWaterMeter()
            database.heatMeterDao().deleteAllHeatMeter()
            database.waterReadingDao().deleteAllWaterReadings()
            database.heatReadingDao().deleteAllHeatReadings()
            emit(Resource.Success("The database was cleared successfully"))
        }catch (e:Exception){
            emit(Resource.Error("Error. Try again"))
        }
    }
}