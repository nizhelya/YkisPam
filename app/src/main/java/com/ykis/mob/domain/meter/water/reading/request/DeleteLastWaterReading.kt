package com.ykis.mob.domain.meter.water.reading.request

import com.ykis.mob.R
import com.ykis.mob.core.Resource
import com.ykis.mob.core.snackbar.SnackbarManager
import com.ykis.mob.data.cache.database.AppDatabase
import com.ykis.mob.data.remote.core.BaseResponse
import com.ykis.mob.domain.meter.water.reading.WaterReadingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DeleteLastWaterReading @Inject constructor(
private val repository: WaterReadingRepository,
private val database: AppDatabase
) {
    operator fun invoke(readingId : Int , uid:String): Flow<Resource<BaseResponse?>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.deleteLastWaterReading(
                readingId,uid
            )
            if(response.success==1){
                emit(Resource.Success(response))
                database.waterReadingDao().deleteWaterReadingById(readingId)
            }
        }catch (e: HttpException) {
            SnackbarManager.showMessage(e.message())
            emit(Resource.Error())
        } catch (e: IOException) {
            SnackbarManager.showMessage(R.string.error_network)
            emit(Resource.Error())
        }
    }
}