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
            apiService.addNewHeatReading(
                createAddNewReadingMap(
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

    private fun createGetHeatReadingMap(
        teplomerId: Int,
        uid: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map.put(ApiService.TEPLOMER_ID, teplomerId.toString())
        map.put(ApiService.PARAM_USER_ID, uid)
        return map
    }

    private fun createAddNewReadingMap(
        teplomerId: Int,
        newValue: Double,
        currentValue: Double,
        uid: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map.put(ApiService.TEPLOMER_ID, teplomerId.toString())
        map.put(ApiService.NEW_VALUE, newValue.toString())
        map.put(ApiService.CURRENT_VALUE, currentValue.toString())
        map.put(ApiService.PARAM_USER_ID, uid)
        return map
    }

    private fun createDeleteWaterReadingMap(
        pokId: Int,
        uid: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map.put(ApiService.POK_ID, pokId.toString())
        map.put(ApiService.PARAM_USER_ID, uid)
        return map
    }
}