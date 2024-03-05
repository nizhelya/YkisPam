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

class GetHeatReadings @Inject constructor(
    private val repository: HeatReadingRepository,
    private val database: AppDatabase
) {
    operator fun invoke(teplomerId:Int , uid:String): Flow<Resource<List<HeatReadingEntity>?>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getHeatReadings(teplomerId,uid)
            val readingList = database.heatReadingDao().getHeatReading(teplomerId)
            if(readingList.isNotEmpty()){
                emit(Resource.Success(readingList))
            }
            if(response.success==1){
                emit(Resource.Success(response.heatReadings))
                database.heatReadingDao().insertHeatReading(response.heatReadings)
            }
        }catch (e: HttpException) {
            SnackbarManager.showMessage(e.message())
            emit(Resource.Error())
        } catch (e: IOException) {
            val readingList = database.heatReadingDao().getHeatReading(teplomerId)
            if(readingList.isNotEmpty()){
                emit(Resource.Success(readingList))
            }
            SnackbarManager.showMessage(R.string.error_network)
            emit(Resource.Error())
        }
    }
}