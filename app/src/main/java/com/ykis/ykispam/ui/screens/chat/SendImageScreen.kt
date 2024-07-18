package com.ykis.ykispam.ui.screens.chat

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ykis.ykispam.ui.components.appbars.DefaultAppBar
import com.ykis.ykispam.ui.theme.YkisPAMTheme

@Composable
fun SendImageScreen(
    modifier: Modifier = Modifier,
    imageUri : Uri,
    navigateBack : () -> Unit,
    messageText : String,
    onMessageTextChanged : (String) -> Unit,
    onSent : () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        DefaultAppBar(
            title = "",
            canNavigateBack = true,
            onBackClick = {
                navigateBack()
            }
        )
        Box(
            modifier = modifier.weight(1f).align(Alignment.CenterHorizontally)
        ){
            AsyncImage(
                model = imageUri,
                contentDescription = "",
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.Center),
                contentScale = ContentScale.Fit,
            )
        }


        ComposeMessageBox(
            onSent = { onSent() },
            onImageSent = {} ,
            text = messageText,
            showAttachIcon = false ,
            onTextChanged = onMessageTextChanged
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
            onSent = {}
        )
    }
}