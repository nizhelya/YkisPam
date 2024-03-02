package com.ykis.ykispam.domain.meter.water.meter.request

import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.data.cache.database.AppDatabase
import com.ykis.ykispam.domain.meter.water.meter.WaterMeterEntity
import com.ykis.ykispam.domain.meter.water.meter.WaterMeterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetWaterMeterList @Inject constructor(
    private val repository: WaterMeterRepository,
    private val database: AppDatabase
) {
    operator fun invoke(addressId:Int , uid:String): Flow<Resource<List<WaterMeterEntity>?>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getWaterMeterList(
                addressId,uid
            )
            if(response.success==1){
                emit(Resource.Success(response.waterMeters))
                database.waterMeterDao().insertWaterMeter(response.waterMeters)
            }
        }catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error!"))
        } catch (e: IOException) {
            val waterMeterList = database.waterMeterDao().getWaterMeter(addressId)
            if(waterMeterList.isNotEmpty()){
                emit(Resource.Success(waterMeterList))
                return@flow
            }
            emit(Resource.Error("Check your internet connection"))
        }
    }
}