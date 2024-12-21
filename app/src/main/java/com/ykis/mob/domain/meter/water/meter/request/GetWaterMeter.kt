package com.ykis.mob.domain.meter.water.meter.request

import com.ykis.mob.R
import com.ykis.mob.core.Resource
import com.ykis.mob.core.snackbar.SnackbarManager
import com.ykis.mob.data.cache.database.AppDatabase
import com.ykis.mob.domain.meter.water.meter.WaterMeterEntity
import com.ykis.mob.domain.meter.water.meter.WaterMeterRepository
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
            val waterMeterList = database.waterMeterDao().getWaterMeter(addressId)
            if(waterMeterList.isNotEmpty()){
                emit(Resource.Success(waterMeterList))
            }
            if(response.success==1){
                emit(Resource.Success(response.waterMeters))
                database.waterMeterDao().insertWaterMeter(response.waterMeters)
            }
        }catch (e: HttpException) {
            SnackbarManager.showMessage(e.message())
            emit(Resource.Error())
        } catch (e: IOException) {
            val waterMeterList = database.waterMeterDao().getWaterMeter(addressId)
            if(waterMeterList.isNotEmpty()){
                emit(Resource.Success(waterMeterList))
                return@flow
            }
            SnackbarManager.showMessage(R.string.error_network)
            emit(Resource.Error())
        }
    }
}