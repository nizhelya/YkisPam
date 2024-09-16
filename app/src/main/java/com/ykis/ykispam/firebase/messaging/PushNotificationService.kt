package com.ykis.ykispam.firebase.messaging

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotificationService : FirebaseMessagingService() {
    companion object {
        private const val TAG = "token_test"
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("token_test", "Refreshed token: $token")
        // Send token to server
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("token_test", "Message data: ${remoteMessage.data}")
        // Display push notification
    }
}