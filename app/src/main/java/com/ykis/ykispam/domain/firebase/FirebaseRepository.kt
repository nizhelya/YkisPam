package com.ykis.ykispam.domain.firebase

import com.ykis.ykispam.data.remote.GetSimpleResponse

interface FirebaseRepository {
    suspend fun sendNotificationToUser(sendNotificationArguments : SendNotificationArguments) : GetSimpleResponse
}