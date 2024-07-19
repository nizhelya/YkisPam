package com.ykis.ykispam.ui.screens.chat

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.ykis.ykispam.ui.components.ZoomableImage

@Composable
fun ImageDetailScreen(
    modifier: Modifier = Modifier,
    navigateUp : () -> Unit,
    messageEntity: MessageEntity
) {
    Box(
        modifier = modifier.fillMaxSize(    )
    ){
        Column {
            ZoomableImage(
                modifier= modifier.weight(1f),
                imageUri = messageEntity.imageUrl?.toUri() ?: Uri.EMPTY
            )
            Text(
                modifier = modifier.padding(16.dp),
                text = messageEntity.text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        IconButton(
            modifier = modifier.padding(8.dp),
            onClick = { navigateUp() }
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                null
            )
        }
    }
}