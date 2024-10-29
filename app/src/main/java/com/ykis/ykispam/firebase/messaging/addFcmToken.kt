package com.ykis.ykispam.firebase.messaging

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.google.firebase.messaging.FirebaseMessaging


fun addFcmToken(){
    val firebaseMessaging = FirebaseMessaging.getInstance()
    firebaseMessaging.token.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val token = task.result
            val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
            Log.d("token_test", "token: $token")
            if (currentUserUid != null) {
                val userRef = Firebase.firestore.collection("users").document(currentUserUid)
                userRef.update("fcmTokens", FieldValue.arrayUnion(token))
                    .addOnSuccessListener {
                        Log.d("FCM", "FCM Token successfully added!")
                    }
                    .addOnFailureListener { e ->
                        Log.w("FCM", "Error adding FCM Token", e)
                    }
            }
        } else {
            Log.w("FCM", "Failed to get FCM token")
        }
    }
}