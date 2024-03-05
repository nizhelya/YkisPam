package com.ykis.ykispam.domain.meter.water.reading.request


import com.ykis.ykispam.R
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.data.cache.database.AppDatabase
import com.ykis.ykispam.domain.meter.water.reading.WaterReadingEntity
import com.ykis.ykispam.domain.meter.water.reading.WaterReadingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetWaterReadings @Inject constructor(
    private val repository: WaterReadingRepository,
    private val database: AppDatabase
) {
    operator fun invoke(vodomerId:Int , uid:String): Flow<Resource<List<WaterReadingEntity>?>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getWaterReadings(
                vodomerId, uid
            )
            val readingList = database.waterReadingDao().getWaterReadings(vodomerId)
            if(readingList.isNotEmpty()){
                emit(Resource.Success(readingList))
            }
            if(response.success==1){
                emit(Resource.Success(response.waterReadings))
                database.waterReadingDao().insertWaterReading(response.waterReadings)
            }
        }catch (e: HttpException) {
            SnackbarManager.showMessage(e.message())
            emit(Resource.Error())
        } catch (e: IOException) {
            val readingList = database.waterReadingDao().getWaterReadings(vodomerId)
            if(readingList.isNotEmpty()){
                emit(Resource.Success(readingList))
                return@flow
            }
            SnackbarManager.showMessage(R.string.error_network)
            emit(Resource.Error())
        }
    }
}