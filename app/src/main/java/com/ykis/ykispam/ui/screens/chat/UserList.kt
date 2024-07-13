package com.ykis.ykispam.ui.screens.chat

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.ykis.ykispam.ui.BaseUIState

@Composable
fun UserList(
    modifier: Modifier = Modifier,
    baseUIState: BaseUIState,
    userList : List<UserEntity>,
    onUserClick : (UserEntity) -> Unit,
    chatViewModel: ChatViewModel
) {
    LazyColumn {
        items(
           items =  userList,
            key = {
                it.uid
            }
        ){
            var latestMessageEntity by remember {
                mutableStateOf(MessageEntity())
            }
            LaunchedEffect(key1 = true) {
                chatViewModel.addChatListener(
                    "${baseUIState.userRole.codeName}_${it.uid}",
                        onLastMessageChange = {
                            message ->
                            latestMessageEntity = message
                        }
                    )
            }
            if(baseUIState.uid != it.uid){
                UserListItem(it = it, onUserClick = onUserClick, lastMessage = latestMessageEntity )
            }
        }
    }
}