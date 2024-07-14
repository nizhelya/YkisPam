package com.ykis.ykispam.ui.screens.chat

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ykis.ykispam.domain.UserRole
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.components.appbars.DefaultAppBar
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    userEntity: UserEntity,
    chatViewModel : ChatViewModel,
    baseUIState : BaseUIState,
    navigateBack : () -> Unit
) {
    val messageText by chatViewModel.messageText.collectAsStateWithLifecycle()
    val messageList by chatViewModel.firebaseTest.collectAsStateWithLifecycle()
    val selectedService by chatViewModel.selectedService.collectAsStateWithLifecycle()
    val chatUid = remember(baseUIState, userEntity) {
        if (baseUIState.userRole == UserRole.StandardUser) {
            baseUIState.uid.toString()
        } else userEntity.uid
    }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var isFirstTime by remember { mutableStateOf(true) }

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
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        DefaultAppBar(
            modifier = modifier,
            title = if (baseUIState.userRole == UserRole.StandardUser) selectedService.name
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
            items(
                messageList,
                ) {
                MessageListItem(uid = baseUIState.uid.toString(), messageEntity = it)
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
                            onComplete = {
                                coroutineScope.launch {
                                    listState.animateScrollToItem(messageList.size-1)
                                }
                            }

                        )
                },
                text = messageText,
                onTextChanged = {chatViewModel.onMessageTextChanged(it)}
            )
        }
}