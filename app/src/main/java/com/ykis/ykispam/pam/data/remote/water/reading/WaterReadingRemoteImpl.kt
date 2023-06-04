package com.ykis.ykispam.pam.data.remote.water.reading

import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.data.remote.api.ApiService
import com.ykis.ykispam.pam.data.remote.core.Request
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import com.ykis.ykispam.pam.domain.water.reading.WaterReadingEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WaterReadingRemoteImpl @Inject constructor(
    private val request: Request,
    private val apiService: ApiService
) : WaterReadingRemote {
    override fun getWaterReadings(
        vodomerId: Int,
        userId: Int,
        token: String
    ): Either<Failure, List<WaterReadingEntity>> {
        return request.make(
            apiService.getWaterReadings(
                createGetWaterReadingMap(
                    vodomerId,
                    userId,
                    token
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
        userId: Int,
        token: String
    ): Either<Failure, GetSimpleResponse> {
        return request.make(
            apiService.addNewWaterReading(
                createAddNewReadingMap(
                    vodomerId,
                    newValue,
                    currentValue,
                    userId,
                    token
                )
            )
        )
        {
            it
        }
    }

    override fun deleteCurrentWaterReading(
        pokId: Int,
        userId: Int,
        token: String
    ): Either<Failure, GetSimpleResponse> {
        return request.make(
            apiService.deleteCurrentWaterReading(
                createDeleteWaterReadingMap(
                    pokId,
                    userId,
                    token
                )
            )
        )
        {
            it
        }
    }

    private fun createGetWaterReadingMap(
        vodomerId: Int,
        userId: Int,
        token: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map.put(ApiService.VODOMER_ID, vodomerId.toString())
        map.put(ApiService.PARAM_USER_ID, userId.toString())
        map.put(ApiService.PARAM_TOKEN, token)
        return map
    }

    private fun createAddNewReadingMap(
        vodomerId: Int,
        newValue: Int,
        currentValue: Int,
        userId: Int,
        token: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map.put(ApiService.VODOMER_ID, vodomerId.toString())
        map.put(ApiService.NEW_VALUE, newValue.toString())
        map.put(ApiService.CURRENT_VALUE, currentValue.toString())
        map.put(ApiService.PARAM_USER_ID, userId.toString())
        map.put(ApiService.PARAM_TOKEN, token)
        return map
    }

    private fun createDeleteWaterReadingMap(
        pokId: Int,
        userId: Int,
        token: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map.put(ApiService.POK_ID, pokId.toString())
        map.put(ApiService.PARAM_USER_ID, userId.toString())
        map.put(ApiService.PARAM_TOKEN, token)
        return map
    }
}