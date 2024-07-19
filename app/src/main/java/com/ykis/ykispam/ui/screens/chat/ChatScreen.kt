package com.ykis.ykispam.ui.screens.chat

import MessageListItem
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ykis.ykispam.domain.UserRole
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.components.appbars.DefaultAppBar
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

sealed class ChatItem {
    data class DateHeader(val date: String) : ChatItem()
    data class MessageItem(val message: MessageEntity) : ChatItem()
}

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    userEntity: UserEntity,
    chatViewModel: ChatViewModel,
    baseUIState: BaseUIState,
    navigateBack: () -> Unit,
    navigateToSendImageScreen : () -> Unit,
    chatUid : String,
    navigateToCameraScreen : () -> Unit,
    navigateToImageDetailScreen : (MessageEntity) -> Unit
) {
    val messageText by chatViewModel.messageText.collectAsStateWithLifecycle()
    val messageList by chatViewModel.firebaseTest.collectAsStateWithLifecycle()
    val selectedService by chatViewModel.selectedService.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var isFirstTime by remember { mutableStateOf(true) }
    val isLoadingAfterSending by chatViewModel.isLoadingAfterSending.collectAsStateWithLifecycle()
    var showDeleteMessageDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var selectedMessageId by rememberSaveable {
        mutableStateOf("")
    }
    LaunchedEffect(key1 = baseUIState.uid) {
        chatViewModel.readFromDatabase(
            role = baseUIState.userRole,
            chatUid
        )
    }
    LaunchedEffect(key1 = messageList) {
        Log.d("messages_test", messageList.toString())
    }
    LaunchedEffect(key1 = messageList) {
        Log.d("messages_test", messageList.toString())
        if (isFirstTime && messageList.isNotEmpty()) {
            coroutineScope.launch {
                listState.scrollToItem(messageList.size - 1)
            }
            isFirstTime = false
        }
    }

    // Group messages by date
    val groupedMessages = remember(messageList) {
        messageList.groupBy { formatDate(it.timestamp) }
    }

    // Flatten grouped messages into a list of ChatItem
    val chatItems = remember(groupedMessages) {
        groupedMessages.flatMap { (date, messages) ->
            listOf(ChatItem.DateHeader(date)) + messages.map { ChatItem.MessageItem(it) }
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        DefaultAppBar(
            modifier = modifier,
            title = if (baseUIState.userRole == UserRole.StandardUser) "Чат ${selectedService.name}"
            else {
                userEntity.displayName ?: userEntity.email.toString()
            },
            canNavigateBack = true,
            onBackClick = { navigateBack() }
        )
        LazyColumn(
            modifier = modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            state = listState
        ) {
            items(chatItems) { chatItem ->
                when (chatItem) {
                    is ChatItem.DateHeader -> {
                        Text(
                            text = chatItem.date,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    is ChatItem.MessageItem -> {
                        MessageListItem(
                            uid = baseUIState.uid.toString(),
                            messageEntity = chatItem.message,
                            onLongClick = {
                               selectedMessageId = chatItem.message.id
                                showDeleteMessageDialog = true
                            },
                            onClick = {
                                navigateToImageDetailScreen(chatItem.message)
                            }
                        )
                    }
                }
            }
        }
        ComposeMessageBox(
            onSent = {
                chatViewModel.writeToDatabase(
                    chatUid = chatUid,
                    baseUIState.uid.toString(),
                    if (baseUIState.displayName.isNullOrEmpty()) baseUIState.email.toString() else baseUIState.displayName,
                    senderLogoUrl = baseUIState.photoUrl,
                    role = baseUIState.userRole,
                    senderAddress = if(baseUIState.userRole == UserRole.StandardUser) baseUIState.address else "",
                    onComplete = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(messageList.size - 1)
                        }
                    },
                    imageUrl = null
                )
            },
            onImageSent = {
                chatViewModel.setSelectedImageUri(it)
                navigateToSendImageScreen()
            },
            text = messageText,
            onTextChanged = { chatViewModel.onMessageTextChanged(it) },
            onCameraClick = {
                navigateToCameraScreen()
            },
            isLoading = isLoadingAfterSending,
            canSend = messageText.isNotBlank()
        )
    }
    if(showDeleteMessageDialog){
        DeleteMessageDialog(
            onDismiss = { showDeleteMessageDialog = false },
            onConfirm = {
                chatViewModel.deleteMessageFromDatabase(
                    chatUid = chatUid,
                    messageId = selectedMessageId,
                    role = baseUIState.userRole
                )
                showDeleteMessageDialog = false
            }
        )
    }
}