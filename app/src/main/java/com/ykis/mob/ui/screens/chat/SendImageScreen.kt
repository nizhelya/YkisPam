package com.ykis.mob.ui.screens.chat

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ykis.mob.ui.components.ZoomableImage
import com.ykis.mob.ui.theme.YkisPAMTheme

@Composable
fun SendImageScreen(
    modifier: Modifier = Modifier,
    imageUri : Uri,
    navigateBack : () -> Unit,
    messageText : String,
    onMessageTextChanged : (String) -> Unit,
    onSent : () -> Unit,
    isLoadingAfterSending : Boolean
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ){
           ZoomableImage(imageUri = imageUri)
            IconButton(
                modifier = modifier.padding(8.dp).align(Alignment.TopStart),
                onClick = { navigateBack() }
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    null
                )
            }
        }

        ComposeMessageBox(
            onSent = { onSent() },
            onImageSent = {} ,
            text = messageText,
            showAttachIcon = false ,
            onTextChanged = onMessageTextChanged,
            onCameraClick = {},
            isLoading = isLoadingAfterSending,
            canSend = true
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSendImageScreen() {
    YkisPAMTheme {
        SendImageScreen(
            imageUri = Uri.EMPTY,
            messageText = "" ,
            onMessageTextChanged = {},
            navigateBack = {},
            onSent = {},
            isLoadingAfterSending = false
        )
    }
}