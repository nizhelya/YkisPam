package com.ykis.ykispam.data.remote.heat.reading

import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.data.remote.api.ApiService
import com.ykis.ykispam.data.remote.core.BaseResponse
import com.ykis.ykispam.data.remote.core.Request
import com.ykis.ykispam.domain.meter.heat.reading.AddHeatReadingParams
import com.ykis.ykispam.domain.meter.heat.reading.HeatReadingEntity
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import retrofit2.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeatReadingRemoteImpl @Inject constructor(
    private val request: Request,
    private val apiService: ApiService
) : HeatReadingRemote {
    override fun getHeatReading(
        teplomerId: Int,
        uid: String
    ): Either<Failure, List<HeatReadingEntity>> {
        return request.make(
            apiService.getHeatReadings(
                createGetHeatReadingMap(
                    teplomerId,
                    uid
                )
            )
        )
        {
            it.heatReadings
        }
    }

    override fun addNewHeatReading(
        teplomerId: Int,
        newValue: Double,
        currentValue: Double,
        uid: String
    ): Either<Failure, GetSimpleResponse> {
        return request.make(
            apiService.addHeatReading(
                createAddReadingMap(
                    teplomerId,
                    newValue,
                    currentValue,
                    uid
                )
            )
        )
        {
            it
        }
    }

    override fun deleteCurrentHeatReading(
        pokId: Int,
        uid: String
    ): Either<Failure, GetSimpleResponse> {
        return request.make(
            apiService.deleteCurrentHeatReading(
                createDeleteWaterReadingMap(
                    pokId,
                    uid
                )
            )
        )
        {
            it
        }
    }

    override suspend fun getHeatReadings(teplomerId: Int, uid: String): GetHeatReadingResponse {
        return apiService.getHeatReadings(
            createGetHeatReadingMap(
                teplomerId,uid
            )
        ).await()
    }

    override suspend fun getLastHeatReading(teplomerId: Int , uid:String): GetLastHeatReadingResponse {
       return apiService.getLastHeatReading(
           createGetHeatReadingMap(
               teplomerId, uid
           )
       ).await()
    }

    override suspend fun addHeatReading(params: AddHeatReadingParams): BaseResponse {
        return apiService.addHeatReading(
            createAddReadingMap(
                teplomerId = params.meterId,
                currentValue = params.currentValue,
                newValue = params.newValue,
                uid = params.uid
            )
        ).await()
    }

    private fun createGetHeatReadingMap(
        teplomerId: Int,
        uid: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.TEPLOMER_ID] = teplomerId.toString()
        map[ApiService.UID] = uid
        return map
    }

    private fun createAddReadingMap(
        teplomerId: Int,
        newValue: Double,
        currentValue: Double,
        uid: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.TEPLOMER_ID] = teplomerId.toString()
        map[ApiService.NEW_VALUE] = newValue.toString()
        map[ApiService.CURRENT_VALUE] = currentValue.toString()
        map[ApiService.UID] = uid
        return map
    }

    private fun createDeleteWaterReadingMap(
        pokId: Int,
        uid: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.POK_ID] = pokId.toString()
        map[ApiService.UID] = uid
        return map
    }
}