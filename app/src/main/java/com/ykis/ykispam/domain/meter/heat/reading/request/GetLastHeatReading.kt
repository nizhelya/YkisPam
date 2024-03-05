package com.ykis.ykispam.domain.meter.heat.reading.request

import com.ykis.ykispam.R
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.data.cache.database.AppDatabase
import com.ykis.ykispam.domain.meter.heat.reading.HeatReadingEntity
import com.ykis.ykispam.domain.meter.heat.reading.HeatReadingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetLastHeatReading @Inject constructor(
    private val repository: HeatReadingRepository,
    private val database: AppDatabase
) {
    operator fun invoke(teplomerId:Int , uid:String): Flow<Resource<HeatReadingEntity?>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getLastHeatReading(
                teplomerId, uid
            )
            val lastReading = database.heatReadingDao().getHeatReading(teplomerId)
            if(lastReading.isNotEmpty()){
                emit(Resource.Success(lastReading.last()))
            }
            if(response.success==1){
                emit(Resource.Success(response.heatReading))
                database.heatReadingDao().insertHeatReading(listOf(response.heatReading))
            }
        }catch (e: HttpException) {
            SnackbarManager.showMessage(e.message())
            emit(Resource.Error())
        } catch (e: IOException) {
            val lastReading = database.heatReadingDao().getHeatReading(teplomerId)
            if(lastReading.isNotEmpty()){
                emit(Resource.Success(lastReading.last()))
            }
            SnackbarManager.showMessage(R.string.error_network)
            emit(Resource.Error())
        }
    }
}