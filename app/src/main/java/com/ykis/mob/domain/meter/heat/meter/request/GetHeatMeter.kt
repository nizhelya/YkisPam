package com.ykis.mob.domain.meter.heat.meter.request

import com.ykis.mob.R
import com.ykis.mob.core.Resource
import com.ykis.mob.core.snackbar.SnackbarManager
import com.ykis.mob.data.cache.database.AppDatabase
import com.ykis.mob.domain.meter.heat.meter.HeatMeterEntity
import com.ykis.mob.domain.meter.heat.meter.HeatMeterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

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
            val meterList = database.heatMeterDao().getHeatMeter(addressId)
            if(meterList.isNotEmpty()){
                emit(Resource.Success(meterList))
                return@flow
            }
            if(response.success==1){
                emit(Resource.Success(response.heatMeters))
                database.heatMeterDao().insertHeatMeter(response.heatMeters)
            }
        }catch (e: HttpException) {
            SnackbarManager.showMessage(e.message())
            emit(Resource.Error())
        } catch (e: IOException) {
            val meterList = database.heatMeterDao().getHeatMeter(addressId)
            if(meterList.isNotEmpty()){
                emit(Resource.Success(meterList))
                return@flow
            }
            SnackbarManager.showMessage(R.string.error_network)
            emit(Resource.Error())
        }
    }
}