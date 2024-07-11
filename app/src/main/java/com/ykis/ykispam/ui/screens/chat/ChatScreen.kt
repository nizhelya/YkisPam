package com.ykis.ykispam.ui.screens.chat

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ykis.ykispam.domain.UserRole
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.components.appbars.DefaultAppBar

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
    val chatUid = remember(baseUIState , userEntity) {
        if(baseUIState.userRole == UserRole.StandardUser){
            baseUIState.uid.toString()
        }else userEntity.uid
    }

    LaunchedEffect(key1 = baseUIState.uid) {
        chatViewModel.readFromDatabase(
            role = baseUIState.userRole,
           chatUid
        )
    }
    LaunchedEffect(key1 = messageList) {
        Log.d("messages_test" , messageList.toString())
    }
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        DefaultAppBar(
            modifier = modifier,
            title = if(baseUIState.userRole == UserRole.StandardUser) selectedService.name
            else{
                userEntity.displayName ?: userEntity.email.toString()
            }
            ,
            canNavigateBack = true,
            onBackClick = {navigateBack()}
        )
        LazyColumn(
            modifier = modifier.weight(1f)
        ){
            items(
                messageList,
            ) {
                Column(
                    modifier = modifier.padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = it.email,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(it.text)
                    HorizontalDivider()
                }

            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            OutlinedTextField(
                modifier = modifier.weight(1f),
                singleLine = true,
                value = messageText,
                onValueChange = { chatViewModel.onMessageTextChanged(it) }
            )
            Button(
                modifier = modifier,
                onClick = {
                    Log.d("chat_test" , "onClick")
                    chatViewModel.writeToDatabase(
                        chatUid = chatUid,
                        baseUIState.uid.toString() ,
                        baseUIState.email.toString(),
                        role = baseUIState.userRole
                    )
                }
            ) {
                Text("Відправити")
            }

        }
    }
}