package com.ykis.ykispam.pam.data.remote.heat.reading

import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.data.remote.api.ApiService
import com.ykis.ykispam.pam.data.remote.core.Request
import com.ykis.ykispam.pam.domain.heat.reading.HeatReadingEntity
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeatReadingRemoteImpl @Inject constructor(
    private val request: Request,
    private val apiService: ApiService
) : HeatReadingRemote {
    override fun getHeatReadings(
        teplomerId: Int,
        userId: Int,
        token: String
    ): Either<Failure, List<HeatReadingEntity>> {
        return request.make(
            apiService.getHeatReadings(
                createGetHeatReadingMap(
                    teplomerId,
                    userId,
                    token
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
        userId: Int,
        token: String
    ): Either<Failure, GetSimpleResponse> {
        return request.make(
            apiService.addNewHeatReading(
                createAddNewReadingMap(
                    teplomerId,
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

    override fun deleteCurrentHeatReading(
        pokId: Int,
        userId: Int,
        token: String
    ): Either<Failure, GetSimpleResponse> {
        return request.make(
            apiService.deleteCurrentHeatReading(
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

    private fun createGetHeatReadingMap(
        teplomerId: Int,
        userId: Int,
        token: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map.put(ApiService.TEPLOMER_ID, teplomerId.toString())
        map.put(ApiService.PARAM_USER_ID, userId.toString())
        map.put(ApiService.PARAM_TOKEN, token)
        return map
    }

    private fun createAddNewReadingMap(
        teplomerId: Int,
        newValue: Double,
        currentValue: Double,
        userId: Int,
        token: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map.put(ApiService.TEPLOMER_ID, teplomerId.toString())
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