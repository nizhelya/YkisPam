package com.ykis.ykispam.ui.screens.chat

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.firestore.firestore
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.firebase.service.repo.LogService
import com.ykis.ykispam.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class MessageEntity(
    val uid : String = "" ,
    val email : String = "" ,
    val text :  String = ""
)
data class UserEntity(
    val photoUrl: String?,
    val createdAt: Timestamp?,
    val password: String?,
    val displayName: String?,
    val email: String?
)
fun mapToUserEntity(map: Map<String, Any>): UserEntity {
    return UserEntity(
        photoUrl = map["photoUrl"] as String?,
        createdAt = map["createdAt"] as Timestamp?,
        password = map["password"] as String?,
        displayName = map["displayName"] as String?,
        email = map["email"] as String?
    )
}
@HiltViewModel
class ChatViewModel @Inject constructor(
    logService: LogService
): BaseViewModel(logService){

    private val realtimeDatabase = Firebase.database
    val userDatabase = Firebase.firestore
    val messagesRef = realtimeDatabase.getReference("messages")
    private val _firebaseTest = MutableStateFlow<List<MessageEntity>>(emptyList())
    val firebaseTest = _firebaseTest.asStateFlow()

    private val _messageText = MutableStateFlow("")
    val messageText = _messageText.asStateFlow()

    private val _userList = MutableStateFlow<List<UserEntity>>(emptyList())
    val userList = _userList.asStateFlow()

    init {
        readFromDatabase()
    }
    fun writeToDatabase(uid: String, email : String){

        val id = messagesRef.push().key!!
        messagesRef.child(id).setValue(MessageEntity( uid ,email ,messageText.value)).addOnCompleteListener {
        }.addOnFailureListener {
            err->
            SnackbarManager.showMessage(err.message.toString())
        }
    }

    private fun readFromDatabase(){
        messagesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    _firebaseTest.value = emptyList()
                    for(messageSnap in dataSnapshot.children){
                        val messageData = messageSnap.getValue(MessageEntity::class.java)
                        if(messageData!=null){
                            _firebaseTest.value = firebaseTest.value + listOf(messageData)
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("firebase_error", "Failed to read value.", error.toException())
            }
        })
    }

    fun onMessageTextChanged(value:String){
        _messageText.value = value
    }

    fun getUsers(){
        userDatabase.collection("users")
            .get()
            .addOnSuccessListener { result ->
                _userList.value = emptyList()
                for (document in result) {
                    Log.d("user_store_test", "${document.id} => ${document.data}")
                    _userList.value = userList.value + listOf(mapToUserEntity(document.data))
                }
            }
            .addOnFailureListener { exception ->
                Log.w("user_store_test", "Error getting documents.", exception)
            }
    }
}