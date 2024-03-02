package com.ykis.ykispam.domain.meter.water.reading.request

import android.util.Log
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.data.cache.database.AppDatabase
import com.ykis.ykispam.data.remote.core.BaseResponse
import com.ykis.ykispam.domain.meter.water.AddReadingParams
import com.ykis.ykispam.domain.meter.water.reading.WaterReadingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

//class AddNewWaterReading @Inject constructor(
//    private val waterReadingRepositoryImpl: WaterReadingRepositoryImpl
//) : UseCase<GetSimpleResponse, AddReadingParams>() {
//
//    override suspend fun run(params: AddReadingParams): Either<Failure, GetSimpleResponse> {
//        return waterReadingRepositoryImpl.addNewWaterReading(params)
//    }
//}

class AddWaterReading @Inject constructor(
    private val repository: WaterReadingRepository,
    private val database: AppDatabase
) {
    operator fun invoke(addReadingParams: AddReadingParams): Flow<Resource<BaseResponse?>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.addWaterReading(addReadingParams)
            if(response.success==1){
                emit(Resource.Success(response))
//                database.waterMeterDao().insertWaterMeter(response.waterMeters)
            }
            Log.d("reading_test", response.message)
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
