package com.ykis.ykispam.ui.screens.chat

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ykis.ykispam.ui.BaseUIState

@Composable
fun UserList(
    modifier: Modifier = Modifier,
    baseUIState: BaseUIState,
    userList : List<UserEntity>,
    onUserClick : (UserEntity) -> Unit
) {
    LazyColumn {
        items(userList){
            if(baseUIState.uid != it.uid){
                UserListItem(it = it) {
                        item->
                    onUserClick(item)
                }
            }
        }
    }
}