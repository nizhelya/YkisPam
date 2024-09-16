package com.ykis.ykispam.data.remote.firebase

import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.data.remote.api.ApiService
import com.ykis.ykispam.domain.firebase.SendNotificationArguments
import retrofit2.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseRemoteImpl @Inject constructor(
    private val apiService: ApiService
):FirebaseRemote{
    override suspend fun sendNotificationToUser(sendNotificationArguments: SendNotificationArguments) : GetSimpleResponse {
        return apiService.sendNotificationToUser(createSendNotificationMap(sendNotificationArguments)).await()
    }


    private fun createSendNotificationMap(
        sendNotificationArguments: SendNotificationArguments
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map[ApiService.RECIPIENT_TOKEN] = sendNotificationArguments.recipientToken
        map[ApiService.TITLE] = sendNotificationArguments.title
        map[ApiService.BODY] = sendNotificationArguments.body
        return map
    }
}