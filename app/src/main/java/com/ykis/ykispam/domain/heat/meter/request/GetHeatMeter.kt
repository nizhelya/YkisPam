package com.ykis.ykispam.domain.heat.meter.request

import android.util.Log
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.data.cache.database.AppDatabase
import com.ykis.ykispam.domain.heat.meter.HeatMeterEntity
import com.ykis.ykispam.domain.heat.meter.HeatMeterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

//class GetHeatMeter @Inject constructor(
//    private val heatMeterRepositoryImpl: HeatMeterRepositoryImpl
//) : UseCase<List<HeatMeterEntity>, BooleanInt>() {
//
//    override suspend fun run(params: BooleanInt): Either<Failure, List<HeatMeterEntity>> {
//        return heatMeterRepositoryImpl.getHeatMeter(params)
//    }
//}
class GetHeatMeterList @Inject constructor(
    private val repository: HeatMeterRepository,
    private val database: AppDatabase
) {
    operator fun invoke(addressId:Int , uid:String): Flow<Resource<List<HeatMeterEntity>?>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getHeatMeterList(
                addressId,uid
            )
            Log.d("heat_test", "success" + response.success.toString())
            Log.d("heat_test", "msg" + response.message.toString())
//            if(response.success==1){
                emit(Resource.Success(response.heatMeters))
                database.heatMeterDao().insertHeatMeter(response.heatMeters)
//            }
        }catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error!"))
        } catch (e: IOException) {
//            val MeterList = database.waterMeterDao().getWaterMeter(addressId)
//            if(waterMeterList.isNotEmpty()){
//                emit(Resource.Success(waterMeterList))
//                return@flow
//            }
            emit(Resource.Error("Check your internet connection"))
        }
    }
}