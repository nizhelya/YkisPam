package com.ykis.ykispam.ui.screens.chat

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
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

fun generateChatId(uid1: String, uid2: String): String {
    return if (uid1 < uid2) {
        uid1 + uid2
    } else {
        uid2 + uid1
    }
}
data class MessageEntity(
    val id : String = "",
    val senderUid : String = "",
    val email : String = "",
    val text :  String = ""
)
data class UserEntity(
    val uid : String ="",
    val photoUrl: String? = "",
    val createdAt: Timestamp? = null,
    val password: String? = "",
    val displayName: String? = "",
    val email: String? = ""
)
fun mapToUserEntity(uid:String ,map: Map<String, Any>): UserEntity {
    return UserEntity(
        uid = uid,
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

    private val _firebaseTest = MutableStateFlow<List<MessageEntity>>(emptyList())
    val firebaseTest = _firebaseTest.asStateFlow()

    private val _messageText = MutableStateFlow("")
    val messageText = _messageText.asStateFlow()

    private val _userList = MutableStateFlow<List<UserEntity>>(emptyList())
    val userList = _userList.asStateFlow()

    private val _selectedUser = MutableStateFlow<UserEntity>(UserEntity())
    val selectedUser = _selectedUser.asStateFlow()

    fun writeToDatabase(uid: String, email : String){
        Log.d("chat_test" , "функция writeToDatabase")
        val chatId = generateChatId(uid,selectedUser.value.uid)
        Log.d("chat_test" , "chatId $chatId")
        val reference = FirebaseDatabase.getInstance().getReference("chats").child(chatId)
        val key = reference.push().key!!
        Log.d("chat_test" , "key $key")
        val messageEntity = MessageEntity(
            id = key,
            senderUid = uid,
            email = email,
            text = messageText.value
        )
        Log.d("chat_test" , "messageEntity $messageEntity")
        reference.child(key)
            .setValue(messageEntity).addOnCompleteListener {
                _messageText.value = ""
                Log.d("chat_test" , "completed")
            }.addOnFailureListener {
                SnackbarManager.showMessage(it.message.toString())
                Log.d("chat_test" , "failure ${it.message}")
            }
    }

    fun readFromDatabase(
        uid:String
    ){
        val chatId = generateChatId(uid,selectedUser.value.uid)
        Log.d("read_test" , chatId.toString())
        FirebaseDatabase.getInstance().getReference("chats")
            .child(chatId)
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        _firebaseTest.value = emptyList()
                        for (messageSnap in dataSnapshot.children) {
                            val messageData = messageSnap.getValue(MessageEntity::class.java)
                            if (messageData != null) {
                                _firebaseTest.value = firebaseTest.value + listOf(messageData)
                            }

                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.w("firebase_error", "Failed to read value.", error.toException())
                        SnackbarManager.showMessage(error.message)
                    }
                }
            )
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
                    _userList.value = userList.value + listOf(mapToUserEntity(document.id ,document.data))
                }
            }
            .addOnFailureListener { exception ->
                Log.w("user_store_test", "Error getting documents.", exception)
                SnackbarManager.showMessage(exception.message.toString())
            }
    }

    fun setSelectedUser(user:UserEntity){
        _selectedUser.value = user
    }
}