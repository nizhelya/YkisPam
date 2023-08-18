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

    private fun createGetWaterReadingMap(
        vodomerId: Int,
        uid: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map.put(ApiService.VODOMER_ID, vodomerId.toString())
        map.put(ApiService.PARAM_USER_ID, uid)
        return map
    }

    private fun createAddNewReadingMap(
        vodomerId: Int,
        newValue: Int,
        currentValue: Int,
        uid: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map.put(ApiService.VODOMER_ID, vodomerId.toString())
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