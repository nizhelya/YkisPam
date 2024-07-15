package com.ykis.ykispam.ui.screens.chat

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.firestore
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.domain.UserRole
import com.ykis.ykispam.firebase.service.repo.LogService
import com.ykis.ykispam.ui.BaseViewModel
import com.ykis.ykispam.ui.navigation.ContentDetail
import com.ykis.ykispam.ui.screens.service.list.TotalServiceDebt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class ServiceWithCodeName(
    val name : String = "",
    val codeName : String = ""
)
data class MessageEntity(
    val id: String = "",
    val senderUid: String = "",
    val senderDisplayedName : String = "",
    val senderLogoUrl : String? = null,
    val text:  String = "",
    val timestamp: Long =  0L
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

    val userDatabase = Firebase.firestore

    private val _firebaseTest = MutableStateFlow<List<MessageEntity>>(emptyList())
    val firebaseTest = _firebaseTest.asStateFlow()

    private val _messageText = MutableStateFlow("")
    val messageText = _messageText.asStateFlow()

    private val _userList = MutableStateFlow<List<UserEntity>>(emptyList())
    val userList = _userList.asStateFlow()

    private val _selectedUser = MutableStateFlow<UserEntity>(UserEntity())
    val selectedUser = _selectedUser.asStateFlow()

    private val _selectedService = MutableStateFlow(ServiceWithCodeName())
    val selectedService = _selectedService.asStateFlow()

    private val _userIdentifiersWithRole = MutableStateFlow<List<String>>(emptyList())
    val userIdentifiersWithRole = _userIdentifiersWithRole.asStateFlow()

    fun writeToDatabase(
        chatUid : String,
        senderUid: String,
        senderDisplayedName : String,
        senderLogoUrl: String?,
        onComplete : () -> Unit,
//        senderAddress : String,
        role : UserRole,
    ){
        Log.d("chat_test" , "функция writeToDatabase")
        val chatId = if(role==UserRole.StandardUser){
            "${selectedService.value.codeName}_$senderUid"
        }else "${role.codeName}_$chatUid"
        Log.d("chat_test" , "chatId $chatId")
        val reference = FirebaseDatabase.getInstance().getReference("chats").child(chatId)
        val key = reference.push().key!!
        Log.d("chat_test" , "key $key")
        val messageEntity = MessageEntity(
            id = key,
            senderUid = senderUid,
            text = messageText.value,
            senderLogoUrl = senderLogoUrl,
            senderDisplayedName = senderDisplayedName,
            timestamp = Timestamp.now().seconds * 1000
        )
        Log.d("chat_test" , "messageEntity $messageEntity")
        reference.child(key)
            .setValue(
                messageEntity
            ).addOnCompleteListener {
                _messageText.value = ""
                onComplete()
                Log.d("chat_test" , "completed")
            }.addOnFailureListener {
                SnackbarManager.showMessage(it.message.toString())
                Log.d("chat_test" , "failure ${it.message}")
            }
    }

    fun readFromDatabase(role: UserRole, uid: String) {
        val chatId = if (role == UserRole.StandardUser) {
            "${selectedService.value.codeName}_$uid"
        } else "${role.codeName}_$uid"

        Log.d("read_test", chatId.toString())
        FirebaseDatabase.getInstance().getReference("chats")
            .child(chatId)
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val messageList = mutableListOf<MessageEntity>()
                        val latestMessages = mutableMapOf<String, MessageEntity>()
                        for (messageSnap in dataSnapshot.children) {
                            Log.d("time_test1" , messageSnap.children.toString())
                            val messageData = messageSnap.getValue(MessageEntity::class.java)
                            if (messageData != null) {
                                messageList.add(messageData)
                                latestMessages[chatId] = messageData
                            }
                        }
                        _firebaseTest.value = messageList
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

    fun trackUserIdentifiersWithRole(role: UserRole) {
        val reference = FirebaseDatabase.getInstance().getReference("chats")
        reference.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val userIdentifiers = mutableListOf<String>()
                    for (chatSnap in dataSnapshot.children) {
                        val chatId = chatSnap.key ?: continue
                        if (chatId.startsWith(role.codeName)) {
                            val userId = chatId.substringAfter("_")
                            userIdentifiers.add(userId)
                        }
                    }
                    Log.d("user_ids" , "$userIdentifiers")
                    _userIdentifiersWithRole.value = userIdentifiers
                    getUsers()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("firebase_error", "Failed to read value.", error.toException())
                    SnackbarManager.showMessage(error.message)
                }
            }
        )
    }


    fun getUsers() {
        userDatabase.collection("users")
            .get()
            .addOnSuccessListener { result ->
                _userList.value = emptyList()
                val userIdentifiers = _userIdentifiersWithRole.value
                val filteredUsers = result.documents.mapNotNull { document ->
                    val user = mapToUserEntity(document.id, document.data?.toMap() ?: emptyMap())
                    Log.d("filtered_test" , "userEntity: $user")
                    if (user.uid in userIdentifiers) user else null
                }
                Log.d("filtered_test" , "filteredUsers $filteredUsers")
                _userList.value = filteredUsers
                for (user in filteredUsers) {
                    Log.d("user_store_test", "${user.uid} => ${user.email}")
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

    fun setSelectedService(totalServiceDebt:TotalServiceDebt){
        val service = ServiceWithCodeName(
            name = totalServiceDebt.name,
            codeName = when(totalServiceDebt.contentDetail){
                ContentDetail.WARM_SERVICE -> UserRole.YtkeUser.codeName
                ContentDetail.WATER_SERVICE-> UserRole.VodokanalUser.codeName
                ContentDetail.OSBB -> UserRole.OsbbUser.codeName
                else -> UserRole.TboUser.codeName
            }
        )
        _selectedService.value = service
    }

    fun addChatListener(chatUid: String , onLastMessageChange : (MessageEntity) -> Unit) {
        val reference = FirebaseDatabase.getInstance().getReference("chats").child(chatUid).limitToLast(1)
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val latestMessage = dataSnapshot.children.lastOrNull()?.getValue(MessageEntity::class.java)
                // Log the latest message
                latestMessage?.let {
                    Log.d("chat_listener", "Latest message in chat $chatUid: ${it.text}")
                } ?: run {
                    Log.d("chat_listener", "No messages found in chat $chatUid.")
                }
                onLastMessageChange(latestMessage ?: MessageEntity())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("firebase_error", "Failed to read value for chat $chatUid.", error.toException())
            }
        })
    }
}