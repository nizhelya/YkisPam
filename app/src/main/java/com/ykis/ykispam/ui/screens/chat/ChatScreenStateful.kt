package com.ykis.ykispam.ui.screens.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ykis.ykispam.ui.BaseUIState

@Composable
fun ChatScreenStateful(
    modifier: Modifier = Modifier,
    baseUIState: BaseUIState,
    chatViewModel: ChatViewModel = hiltViewModel()
) {

    val testList by chatViewModel.firebaseTest.collectAsStateWithLifecycle()
    val messageText by chatViewModel.messageText.collectAsStateWithLifecycle()
    val userList by chatViewModel.userList.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true) {
        chatViewModel.getUsers()
    }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = modifier,
            text = "Ваша роль: ${baseUIState.userRole.codeName}",
            style = MaterialTheme.typography.headlineSmall
        )
        LazyColumn(
            modifier = modifier.weight(1f)
        ){
            items(testList) {
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
            modifier = modifier.imePadding(),
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
                onClick = { chatViewModel.writeToDatabase(baseUIState.uid.toString() , baseUIState.email.toString()) }
            ) {
                Text("Відправити")
            }

        }

    }
//    LazyColumn {
//        items(userList){
//            Text(
//                it.displayName ?: it.email.toString()
//
//            )
//        }
//    }
}