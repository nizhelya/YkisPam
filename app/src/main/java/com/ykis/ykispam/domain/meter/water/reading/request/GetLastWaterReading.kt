package com.ykis.ykispam.domain.meter.water.reading.request

import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.data.cache.database.AppDatabase
import com.ykis.ykispam.domain.meter.water.reading.WaterReadingEntity
import com.ykis.ykispam.domain.meter.water.reading.WaterReadingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetLastWaterReading @Inject constructor(
    private val repository: WaterReadingRepository,
    private val database: AppDatabase
) {
    operator fun invoke(vodomerId:Int , uid:String): Flow<Resource<WaterReadingEntity?>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getLastWaterReading(
                vodomerId, uid
            )
            if(response.success==1){
                emit(Resource.Success(response.waterReading))
//                database.waterMeterDao().insertWaterMeter(response.waterMeters)
            }
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
//        catch (e: Exception){
//            SnackbarManager.showMessage(e.message.toString())
//        }
    }
}