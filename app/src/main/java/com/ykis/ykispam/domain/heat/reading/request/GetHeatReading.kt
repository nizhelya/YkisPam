package com.ykis.ykispam.domain.heat.reading.request

import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.data.cache.database.AppDatabase
import com.ykis.ykispam.domain.heat.reading.HeatReadingEntity
import com.ykis.ykispam.domain.heat.reading.HeatReadingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetHeatReadings @Inject constructor(
    private val repository: HeatReadingRepository,
    private val database: AppDatabase
) {
    operator fun invoke(teplomerId:Int , uid:String): Flow<Resource<List<HeatReadingEntity>?>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getHeatReadings(teplomerId,uid)
//            if(response.success==1){
                emit(Resource.Success(response.heatReadings))
//                database.waterMeterDao().insertWaterMeter(response.waterMeters)
//            }
        }catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error!"))
        } catch (e: IOException) {
//            val waterMeterList = database.waterMeterDao().getWaterMeter(addressId)
//            if(waterMeterList.isNotEmpty()){
//                emit(Resource.Success(waterMeterList))
//                return@flow
//            }
            emit(Resource.Error("Check your internet connection"))
        }
    }
}