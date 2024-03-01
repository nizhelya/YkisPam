package com.ykis.ykispam.data.remote.water.reading

import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.data.remote.api.ApiService
import com.ykis.ykispam.data.remote.core.Request
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import com.ykis.ykispam.domain.water.reading.WaterReadingEntity
import retrofit2.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WaterReadingRemoteImpl @Inject constructor(
    private val request: Request,
    private val apiService: ApiService
) : WaterReadingRemote {
    override fun getWaterReading(
        vodomerId: Int,
        uid: String
    ): Either<Failure, List<WaterReadingEntity>> {
        return request.make(
            apiService.getWaterReadings(
                createGetWaterReadingMap(
                    vodomerId,
                    uid
                )
            )
        )
        {
            it.waterReadings
        }
    }

    override fun addNewWaterReading(
        vodomerId: Int,
        newValue: Int,
        currentValue: Int,
        uid: String
    ): Either<Failure, GetSimpleResponse> {
        return request.make(
            apiService.addNewWaterReading(
                createAddNewReadingMap(
                    vodomerId,
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

    override fun deleteCurrentWaterReading(
        pokId: Int,
        uid: String
    ): Either<Failure, GetSimpleResponse> {
        return request.make(
            apiService.deleteCurrentWaterReading(
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

    override suspend fun getWaterReadings(vodomerId: Int, uid: String):GetWaterReadingsResponse  {
        return apiService.getWaterReadings(
            createGetWaterReadingMap(
                vodomerId, uid
            )
        ).await()
    }

    override suspend fun getLastWaterReading(
        vodomerId: Int,
        uid: String
    ): GetLastWaterReadingResponse {
        return apiService.getLastWaterReading(
            createGetWaterReadingMap(
                vodomerId, uid
            )
        ).await()
    }

    private fun createGetWaterReadingMap(
        vodomerId: Int,
        uid: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.VODOMER_ID] = vodomerId.toString()
        map[ApiService.UID] = uid
        return map
    }

    private fun createAddNewReadingMap(
        vodomerId: Int,
        newValue: Int,
        currentValue: Int,
        uid: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.VODOMER_ID] = vodomerId.toString()
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