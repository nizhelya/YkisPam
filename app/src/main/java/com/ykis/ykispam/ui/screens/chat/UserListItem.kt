package com.ykis.ykispam.ui.screens.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.ui.components.UserImage


@Composable
fun UserListItem(
    modifier: Modifier = Modifier,
    it : UserEntity,
    onUserClick : (UserEntity) -> Unit,
    lastMessage : MessageEntity
) {
    Column(
        modifier = modifier.clickable {
            onUserClick(it)
        }
    ) {
        Row(
            modifier = modifier
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            UserImage(photoUrl = it.photoUrl.toString())
            Text(
                text = it.displayName ?: it.email.toString(),
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Text(
            text =  lastMessage.text
        )
        HorizontalDivider()
    }
}

