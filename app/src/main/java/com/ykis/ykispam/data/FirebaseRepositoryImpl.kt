package com.ykis.ykispam.data

import com.ykis.ykispam.data.remote.GetSimpleResponse
import com.ykis.ykispam.data.remote.firebase.FirebaseRemote
import com.ykis.ykispam.domain.firebase.FirebaseRepository
import com.ykis.ykispam.domain.firebase.SendNotificationArguments
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseRemote : FirebaseRemote
):FirebaseRepository {
    override suspend fun sendNotificationToUser(sendNotificationArguments: SendNotificationArguments) : GetSimpleResponse {
        return firebaseRemote.sendNotificationToUser(sendNotificationArguments)
    }

}