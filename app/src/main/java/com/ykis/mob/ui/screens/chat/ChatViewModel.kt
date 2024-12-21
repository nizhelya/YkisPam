package com.ykis.mob.ui.screens.chat

//import com.google.auth.oauth2.GoogleCredentials
import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.firestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.storage.FirebaseStorage
import com.squareup.moshi.Json
import com.ykis.mob.core.snackbar.SnackbarManager
import com.ykis.mob.domain.UserRole
import com.ykis.mob.firebase.service.repo.LogService
import com.ykis.mob.ui.BaseViewModel
import com.ykis.mob.ui.navigation.ContentDetail
import com.ykis.mob.ui.screens.service.list.TotalServiceDebt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.HttpUrl.Companion.toHttpUrl
import javax.inject.Inject


data class SendNotificationArguments(
    @Json(name = "recipient_token")
    val recipientTokens : List<String>,
    val title : String,
    val body : String
)

data class ServiceWithCodeName(
    val name : String = "",
    val codeName : String = ""
)
data class MessageEntity(
    val id: String = "",
    val senderUid: String = "",
    val senderDisplayedName : String = "",
    val senderLogoUrl : String? = null,
    val senderAddress : String = "",
    val text:  String = "",
    val imageUrl : String? = null,
    val timestamp: Long =  0L
)
data class UserEntity(
    val uid : String ="",
    val photoUrl: String? = "",
    val createdAt: Timestamp? = null,
    val password: String? = "",
    val displayName: String? = "",
    val email: String? = "",
    val osbbRoleId : Int? =  null,
    val tokens : List<String> = emptyList()
)
fun mapToUserEntity(uid:String ,map: Map<String, Any>): UserEntity {
    return UserEntity(
        uid = uid,
        photoUrl = map["photoUrl"] as String?,
        createdAt = map["createdAt"] as Timestamp?,
        password = map["password"] as String?,
        displayName = map["displayName"] as String?,
        email = map["email"] as String?,
        tokens = map["fcmTokens"] as List<String>? ?: emptyList()
    )
}
@HiltViewModel
class ChatViewModel @Inject constructor(
    logService: LogService,
): BaseViewModel(logService) {

    val userDatabase = Firebase.firestore
    private val storageReference = FirebaseStorage.getInstance().reference

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

    private val _selectedImageUri = MutableStateFlow(Uri.EMPTY)
    val selectedImageUri = _selectedImageUri.asStateFlow()

    private val _isLoadingAfterSending = MutableStateFlow(false)
    val isLoadingAfterSending = _isLoadingAfterSending.asStateFlow()

    private val _selectedMessage = MutableStateFlow(MessageEntity())
    val selectedMessage = _selectedMessage.asStateFlow()

    private val functions: FirebaseFunctions = FirebaseFunctions.getInstance()

    fun writeToDatabase(
        chatUid: String,
        senderUid: String,
        senderDisplayedName: String,
        senderLogoUrl: String?,
        senderAddress: String,
        imageUrl: String?,  // Modified parameter to include imageUrl
        osbbId: Int,
        role: UserRole,
        onComplete: () -> Unit,
        recipientTokens: List<String>
    ) {
        _isLoadingAfterSending.value = true
        Log.d("chat_test", "функция writeToDatabase")
        Log.d("fcm_tokens_test" , "tokens: ${recipientTokens}")
        val chatId = when {
            role == UserRole.StandardUser && selectedService.value.codeName == UserRole.OsbbUser.codeName -> {
                "${selectedService.value.codeName}_${osbbId}_$chatUid"
            }

            role == UserRole.StandardUser -> {
                "${selectedService.value.codeName}_$chatUid"
            }

            role == UserRole.OsbbUser -> {
                "${role.codeName}_${osbbId}_$chatUid"
            }

            else -> {
                "${role.codeName}_$chatUid"
            }
        }
        Log.d("chat_test", "chatId $chatId")
        val reference = FirebaseDatabase.getInstance().getReference("chats").child(chatId)
        val key = reference.push().key!!
        Log.d("chat_test", "key $key")
        val messageEntity = MessageEntity(
            id = key,
            senderUid = senderUid,
            text = messageText.value,
            senderLogoUrl = senderLogoUrl,
            senderDisplayedName = senderDisplayedName,
            senderAddress = senderAddress,
            imageUrl = imageUrl,  // Set the imageUrl in MessageEntity
            timestamp = Timestamp.now().seconds * 1000
        )
        Log.d("chat_test", "messageEntity $messageEntity")
        reference.child(key)
            .setValue(
                messageEntity
            ).addOnCompleteListener {
                _isLoadingAfterSending.value = false
                sendPushNotification(
                    SendNotificationArguments(
                        recipientTokens = recipientTokens,
                        title = senderDisplayedName,
                        body = messageText.value
                    )
                )
                _messageText.value = ""
                onComplete()
                Log.d("chat_test", "completed")
            }.addOnFailureListener {
                SnackbarManager.showMessage(it.message.toString())
                Log.d("chat_test", "failure ${it.message}")
            }
    }

    fun readFromDatabase(role: UserRole, senderUid: String, osbbId: Int) {
        val chatId = when {
            role == UserRole.StandardUser && selectedService.value.codeName == UserRole.OsbbUser.codeName -> {
                "${selectedService.value.codeName}_${osbbId}_$senderUid"
            }

            role == UserRole.StandardUser -> {
                "${selectedService.value.codeName}_$senderUid"
            }

            role == UserRole.OsbbUser -> {
                "${role.codeName}_${osbbId}_$senderUid"
            }

            else -> {
                "${role.codeName}_$senderUid"
            }
        }

        Log.d("read_test", chatId.toString())
        FirebaseDatabase.getInstance().getReference("chats")
            .child(chatId)
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val messageList = mutableListOf<MessageEntity>()
                        val latestMessages = mutableMapOf<String, MessageEntity>()
                        for (messageSnap in dataSnapshot.children) {
                            Log.d("time_test1", messageSnap.children.toString())
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

    fun onMessageTextChanged(value: String) {
        _messageText.value = value
    }

    fun trackUserIdentifiersWithRole(role: UserRole, osbbRoleId: Int?) {
        val reference = FirebaseDatabase.getInstance().getReference("chats")
        reference.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val userIdentifiers = mutableListOf<String>()
                    for (chatSnap in dataSnapshot.children) {
                        val chatId = chatSnap.key ?: continue
                        val condition =
                            if (osbbRoleId != null) chatId.startsWith("${role.codeName}_${osbbRoleId}") else chatId.startsWith(
                                role.codeName
                            )
                        if (condition) {
                            Log.d("osbb_test", "osbbRoleId: $osbbRoleId")
                            Log.d("osbb_test", "chatId: $chatId")
                            val userId = if (osbbRoleId != null) {
                                chatId.substringAfter("${osbbRoleId}_")
                            } else chatId.substringAfter("_")
                            Log.d("osbb_test", "userId: $userId")
                            userIdentifiers.add(userId)
                        }
                    }
                    Log.d("user_ids", "$userIdentifiers")
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
                    Log.d("filtered_test", "userEntity: $user")
                    if (user.uid in userIdentifiers) user else null
                }
                Log.d("filtered_test", "filteredUsers $filteredUsers")
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

    fun setSelectedUser(user: UserEntity) {
        _selectedUser.value = user
    }

    fun setSelectedService(totalServiceDebt: TotalServiceDebt) {
        val service = ServiceWithCodeName(
            name = totalServiceDebt.name,
            codeName = when (totalServiceDebt.contentDetail) {
                ContentDetail.WARM_SERVICE -> UserRole.YtkeUser.codeName
                ContentDetail.WATER_SERVICE -> UserRole.VodokanalUser.codeName
                ContentDetail.OSBB -> UserRole.OsbbUser.codeName
                else -> UserRole.TboUser.codeName
            }
        )
        _selectedService.value = service
    }

    fun addChatListener(chatUid: String, onLastMessageChange: (MessageEntity) -> Unit) {
        Log.d("osbb_test", "chatUid111 : $chatUid")
        val reference =
            FirebaseDatabase.getInstance().getReference("chats").child(chatUid).limitToLast(1)
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val latestMessage =
                    dataSnapshot.children.lastOrNull()?.getValue(MessageEntity::class.java)
                // Log the latest message
                latestMessage?.let {
                    Log.d("chat_listener", "Latest message in chat $chatUid: ${it.text}")
                } ?: run {
                    Log.d("chat_listener", "No messages found in chat $chatUid.")
                }
                onLastMessageChange(latestMessage ?: MessageEntity())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(
                    "firebase_error",
                    "Failed to read value for chat $chatUid.",
                    error.toException()
                )
            }
        })
    }

    fun uploadPhotoAndSendMessage(
        chatUid: String,
        senderUid: String,
        senderDisplayedName: String,
        senderLogoUrl: String?,
        senderAddress: String,
        osbbId: Int,
        role: UserRole,
        onComplete: () -> Unit,
        recipientTokens: List<String>
    ) {
        _isLoadingAfterSending.value = true
        val photoRef =
            storageReference.child("chat_images/${selectedImageUri.value.lastPathSegment}")
        photoRef.putFile(selectedImageUri.value)
            .addOnSuccessListener { taskSnapshot ->
                photoRef.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()
                    Log.d("photo_upload", "Image URL: $imageUrl")
                    writeToDatabase(
                        chatUid,
                        senderUid,
                        senderDisplayedName,
                        senderLogoUrl,
                        senderAddress,
                        imageUrl,
                        osbbId,
                        role,
                        onComplete,
                        recipientTokens
                    )
                }
            }
            .addOnFailureListener { exception ->
                Log.w("photo_upload", "Error uploading image.", exception)
                SnackbarManager.showMessage("Error uploading image: ${exception.message}")
            }
    }

    fun setSelectedImageUri(uri: Uri) {
        _selectedImageUri.value = uri
    }

    fun deleteMessageFromDatabase(
        senderUid: String,
        messageId: String,
        role: UserRole,
        osbbId: Int
    ) {
//        val chatId = if (role == UserRole.StandardUser) {
//            "${selectedService.value.codeName}_$chatUid"
//        } else "${role.codeName}_$chatUid"
        val chatId = when {
            role == UserRole.StandardUser && selectedService.value.codeName == UserRole.OsbbUser.codeName -> {
                "${selectedService.value.codeName}_${osbbId}_$senderUid"
            }

            role == UserRole.StandardUser -> {
                "${selectedService.value.codeName}_$senderUid"
            }

            role == UserRole.OsbbUser -> {
                "${role.codeName}_${osbbId}_$senderUid"
            }

            else -> {
                "${role.codeName}_$senderUid"
            }
        }
        Log.d("delete_message", "chatId: $chatId")
        Log.d("delete_message", "messageId: $messageId")
        val reference =
            FirebaseDatabase.getInstance().getReference("chats").child(chatId).child(messageId)
        reference.removeValue()
            .addOnCompleteListener {
                Log.d("delete_message", "Message $messageId deleted successfully from chat $chatId")
            }
            .addOnFailureListener { exception ->
                Log.w(
                    "delete_message",
                    "Error deleting message $messageId from chat $chatId",
                    exception
                )
                SnackbarManager.showMessage("Error deleting message: ${exception.message}")
            }
    }

    fun setSelectedMessage(message: MessageEntity) {
        _selectedMessage.value = message
    }

    fun sendPushNotification(
        sendNotificationArguments: SendNotificationArguments
    ) {
        viewModelScope.launch {
            for(token in sendNotificationArguments.recipientTokens){
                val urlString = "https://sendnotification-ai2rm2uxna-uc.a.run.app?token=${token}&body=${sendNotificationArguments.body}&title=${sendNotificationArguments.title}"
                Log.d("fcm_tokens_test" , "sent not: $urlString")
                functions.getHttpsCallableFromUrl(urlString.toHttpUrl().toUrl()).call()
            }

        }
    }


}