package com.ykis.mob.domain.meter.water.reading.request

import com.ykis.mob.R
import com.ykis.mob.core.Resource
import com.ykis.mob.core.snackbar.SnackbarManager
import com.ykis.mob.data.cache.database.AppDatabase
import com.ykis.mob.domain.meter.water.reading.WaterReadingEntity
import com.ykis.mob.domain.meter.water.reading.WaterReadingRepository
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
            val lastReading = database.waterReadingDao().getWaterReadings(vodomerId)
            if(lastReading.isNotEmpty()){
                emit(Resource.Success(lastReading.last()))
            }
            if (response.success == 1) {
                emit(Resource.Success(response.waterReading))
                database.waterReadingDao().insertWaterReading(listOf(response.waterReading))
            }
        } catch (e: HttpException) {
            SnackbarManager.showMessage(e.message())
            emit(Resource.Error())
        } catch (e: IOException) {
            val lastReading = database.waterReadingDao().getWaterReadings(vodomerId)
            if(lastReading.isNotEmpty()){
                emit(Resource.Success(lastReading.last()))
            }
            SnackbarManager.showMessage(R.string.error_network)
            emit(Resource.Error())
        }
    }
}