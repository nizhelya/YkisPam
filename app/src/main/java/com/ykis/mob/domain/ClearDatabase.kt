package com.ykis.mob.domain

import android.util.Log
import com.ykis.mob.core.Resource
import com.ykis.mob.data.cache.database.AppDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ClearDatabase @Inject constructor(
    private val database : AppDatabase
){
    operator fun invoke () : Flow<Resource<String>> = flow{
        try{
            database.familyDao().deleteAllFamily()
            database.serviceDao().deleteAllService()
            database.paymentDao().deleteAllPayment()
            database.waterMeterDao().deleteAllWaterMeter()
            database.heatMeterDao().deleteAllHeatMeter()
            database.waterReadingDao().deleteAllWaterReadings()
            database.heatReadingDao().deleteAllHeatReadings()
            Log.d("database","The database was cleared")
        }catch (e:Exception){
            Log.d("database", "The database wasn't cleared ")
        }
    }
}