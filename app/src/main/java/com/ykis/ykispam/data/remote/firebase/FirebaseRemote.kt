package com.ykis.ykispam.data.remote.firebase

import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.domain.firebase.SendNotificationArguments

interface FirebaseRemote {
    suspend fun sendNotificationToUser(sendNotificationArguments : SendNotificationArguments) : GetSimpleResponse
}