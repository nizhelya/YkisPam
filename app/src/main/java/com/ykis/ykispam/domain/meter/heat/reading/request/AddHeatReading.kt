package com.ykis.ykispam.domain.meter.heat.reading.request

import com.ykis.ykispam.R
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.data.cache.database.AppDatabase
import com.ykis.ykispam.data.remote.core.BaseResponse
import com.ykis.ykispam.domain.meter.heat.reading.AddHeatReadingParams
import com.ykis.ykispam.domain.meter.heat.reading.HeatReadingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AddHeatReading @Inject constructor(
    private val repository: HeatReadingRepository,
    private val database: AppDatabase
) {
    operator fun invoke(addReadingParams: AddHeatReadingParams): Flow<Resource<BaseResponse?>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.addHeatReading(addReadingParams)
            if(response.success==1){
                emit(Resource.Success(response))
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
